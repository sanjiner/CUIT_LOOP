package cuit.emergency.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.DelayInformation;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.AdHocCommandDataProvider;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import cc.util.android.core.LogUtil;
import cc.util.android.core.NetUtil;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.android.core.ToastUtil;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.http.DefaultHttpClient;
import cc.util.java.http.HttpResponse;
import cuit.emergency.ConflictActivity;
import cuit.emergency.LoginActivity;
import cuit.emergency.MainActivity;
import cuit.emergency.R;
import cuit.emergency.app.EmergencyService;
import cuit.emergency.fragment.SettingFragment;
import cuit.emergency.util.Constants;
import cuit.emergency.util.ToolUtil;

public class SmackerHelper {
	
	private static SmackerHelper instance = null;
	private Context mContext;
	private String mDefaultServer;
	private String mDefaultServerName;
	
	private final Handler mHandler;
	private final ExecutorService mSingleService;

	//xmpp
	private ConnectionConfiguration mCConfig;
	private XMPPConnection mXMPPConnection;
	private PacketListener mMessageListener;     //新消息监听
	private PacketListener mPresenceListener;    //好友请求等监听
	
	private Vibrator mVibrator;
	
	private List<MultiUserChat> mUserChatList;    //封装进入的群聊
	//database
	private ChatOffline mChatOffline;
	private RosterOffline mRosterOffline;
	private RoomOffline mRoomOffline;

	private SmackerHelper(Context context) {
		this.mContext = context;
		mHandler = new Handler();
		if(SharedPreferenceUtil.getPrefString(mContext, Constants.USERVER, "").equals("")){
			mDefaultServer = LoginActivity.mServer.substring(0, LoginActivity.mServer.indexOf("@"));
			mDefaultServerName = LoginActivity.mServer.substring(mDefaultServer.length()+1, LoginActivity.mServer.length());
		}else{
			mDefaultServer = SharedPreferenceUtil.getPrefString(mContext, Constants.USERVER, "");
			mDefaultServer = mDefaultServer.substring(0, mDefaultServer.indexOf("@"));
			mDefaultServerName = SharedPreferenceUtil.getPrefString(mContext, Constants.USERVER, "");
			mDefaultServerName = mDefaultServerName.substring(mDefaultServer.length()+1, mDefaultServerName.length());
		}
		Constants.DEFAULT_SERVER = mDefaultServer;
		Constants.DEFAULT_SERVERNAME = mDefaultServerName;
		mSingleService = Executors.newSingleThreadExecutor();
		SharedPreferenceUtil.setPrefString(context, Constants.SERVER, Constants.DEFAULT_SERVER);
		String customServer = Constants.DEFAULT_SERVER;
		SharedPreferenceUtil.setPrefInt(context, Constants.PORT, Constants.DEFAULT_PORT_INT);
		int port = Constants.DEFAULT_PORT_INT;
		mCConfig = new ConnectionConfiguration(customServer, port);
		this.mCConfig.setReconnectionAllowed(true);
		this.mCConfig.setSendPresence(false);
		this.mCConfig.setCompressionEnabled(false);
		this.mCConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);  
        this.mCConfig.setSASLAuthenticationEnabled(false); // 是否启用安全验证  
		//收到好友请求后需要经过验证同意
		Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
		this.mCConfig.setRosterLoadedAtLogin(true);
		XMPPConnection.DEBUG_ENABLED = true;
		this.mXMPPConnection = new XMPPConnection(mCConfig);
        configure(ProviderManager.getInstance());
        SmackConfiguration.setPacketReplyTimeout(6 * 1000);
		
		mChatOffline = new ChatOffline(context);
		mRosterOffline = new RosterOffline(context);
		mRoomOffline = new RoomOffline(context);
		mUserChatList = new ArrayList<MultiUserChat>();
	}
	
	public XMPPConnection getmXMPPConnection() {
		return mXMPPConnection;
	}
	
	public synchronized static SmackerHelper newInstance(Context context) {
		if (instance == null) {
			instance = new SmackerHelper(context);
		}
		return instance;
	}
	
	//配置
	private void configure(ProviderManager pm) {
		 // Private Data Storage  
        pm.addIQProvider("query", "jabber:iq:private",  
                new PrivateDataManager.PrivateDataIQProvider());  
  
        // Time  
        try {  
            pm.addIQProvider("query", "jabber:iq:time",
                    Class.forName("org.jivesoftware.smackx.packet.Time"));  
        } catch (ClassNotFoundException e) {  
            
        }  
  
        // Roster Exchange  
        pm.addExtensionProvider("x", "jabber:x:roster",  
                new RosterExchangeProvider());  
  
        // Message Events  
        pm.addExtensionProvider("x", "jabber:x:event",  
                new MessageEventProvider());  
  
        // Chat State  
        pm.addExtensionProvider("active",  
                "http://jabber.org/protocol/chatstates",  
                new ChatStateExtension.Provider());  
        pm.addExtensionProvider("composing",  
                "http://jabber.org/protocol/chatstates",  
                new ChatStateExtension.Provider());  
        pm.addExtensionProvider("paused",  
                "http://jabber.org/protocol/chatstates",  
                new ChatStateExtension.Provider());  
        pm.addExtensionProvider("inactive",  
                "http://jabber.org/protocol/chatstates",  
                new ChatStateExtension.Provider());  
        pm.addExtensionProvider("gone",  
                "http://jabber.org/protocol/chatstates",  
                new ChatStateExtension.Provider());  
  
        // XHTML  
        pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",  
                new XHTMLExtensionProvider());  
  
        // Group Chat Invitations  
        pm.addExtensionProvider("x", "jabber:x:conference",  
                new GroupChatInvitation.Provider());  
  
        // Service Discovery # Items  
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",  
                new DiscoverItemsProvider());  
  
        // Service Discovery # Info  
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",  
                new DiscoverInfoProvider());  
  
        // Data Forms  
        pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());  
  
        // MUC User  
        pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",  
                new MUCUserProvider());  
  
        // MUC Admin  
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",  
                new MUCAdminProvider());  
  
        // MUC Owner  
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",  
                new MUCOwnerProvider());  
  
        // Delayed Delivery  
        pm.addExtensionProvider("x", "jabber:x:delay",  
                new DelayInformationProvider());  
  
        // Version  
        try {  
            pm.addIQProvider("query", "jabber:iq:version",  
                    Class.forName("org.jivesoftware.smackx.packet.Version"));  
        } catch (ClassNotFoundException e) {  
            // Not sure what's happening here.  
        }  
  
        // VCard  
        pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());  
  
        // Offline Message Requests  
        pm.addIQProvider("offline", "http://jabber.org/protocol/offline",  
                new OfflineMessageRequest.Provider());  
  
        // Offline Message Indicator  
        pm.addExtensionProvider("offline",  
                "http://jabber.org/protocol/offline",  
                new OfflineMessageInfo.Provider());  
  
        // Last Activity  
        pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());  
  
        // User Search  
        pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());  
  
        // SharedGroupsInfo  
        pm.addIQProvider("sharedgroup",  
                "http://www.jivesoftware.org/protocol/sharedgroup",  
                new SharedGroupsInfo.Provider());  
  
        // JEP-33: Extended Stanza Addressing  
        pm.addExtensionProvider("addresses",  
                "http://jabber.org/protocol/address",  
                new MultipleAddressesProvider());  
  
        // FileTransfer  
        pm.addIQProvider("si", "http://jabber.org/protocol/si",  
                new StreamInitiationProvider());  
  
        pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams",  
                new BytestreamsProvider());  
  
        // Privacy  
        pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());  
        pm.addIQProvider("command", "http://jabber.org/protocol/commands",  
                new AdHocCommandDataProvider());  
        pm.addExtensionProvider("malformed-action",  
                "http://jabber.org/protocol/commands",  
                new AdHocCommandDataProvider.MalformedActionError());  
        pm.addExtensionProvider("bad-locale",  
                "http://jabber.org/protocol/commands",  
                new AdHocCommandDataProvider.BadLocaleError());  
        pm.addExtensionProvider("bad-payload",  
                "http://jabber.org/protocol/commands",  
                new AdHocCommandDataProvider.BadPayloadError());  
        pm.addExtensionProvider("bad-sessionid",  
                "http://jabber.org/protocol/commands",  
                new AdHocCommandDataProvider.BadSessionIDError());  
        pm.addExtensionProvider("session-expired",  
                "http://jabber.org/protocol/commands",  
                new AdHocCommandDataProvider.SessionExpiredError()); 
	}
	
	/* ------------------ 登录、注销 --------------------------*/
	/**
	 * 登录:登录前首先判断该用户是否存在
	 * @param account
	 * @param password
	 */
	public void login(final String account, final String password, String server) {
		if (NetUtil.state(mContext) == NetUtil.NET_NONE) {
			ConcreteObservable.newInstance().notifyObserver(LoginActivity.class, "网络未连接");
			return;
		}
		mDefaultServer = server.substring(0, server.indexOf("@"));
		mDefaultServerName = server.substring(mDefaultServer.length()+1, server.length());
		Constants.DEFAULT_SERVER = mDefaultServer;
		Constants.DEFAULT_SERVERNAME = mDefaultServerName;
		final String strUrl = "http://"+ Constants.DEFAULT_SERVER +":9090/plugins/presence/status?type=xml&jid="+
			account + "@" + Constants.DEFAULT_SERVERNAME;
		FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				DefaultHttpClient client = new DefaultHttpClient();
				HttpResponse response = client.get(strUrl, null).execute(null);
				StringBuilder builder = new StringBuilder();
				if (response.getStatusCode() == 200) {
					response.read(builder);
					if (builder.toString().indexOf("type=\"error\"") >= 0) {
						return "该用户不存在";
					} else {
						try {
							mXMPPConnection.disconnect();
							mXMPPConnection.connect();
							if (!mXMPPConnection.isAuthenticated())
								mXMPPConnection.login(account, password, Constants.DEFAULT_BINDSOURCE);
							if (!isAuthenticated()) return "登录失败";
							SharedPreferenceUtil.setPrefString(mContext, Constants.UNICKNAME, account);
							registerAllListener();
							setStatusFromConfig();
							return "登录成功";
						} catch (XMPPException e) {
							LogUtil.e(""+e.getMessage()+"---"+e.getLocalizedMessage());
							if (e instanceof XMPPException) {
								if (e.getMessage().contains("not-authorized(401)")){
									return "用户名与密码不匹配";
								}
							} 
							return "连接服务器失败";
						}
					}
				} else if (response.getStatusCode() >= 400){
					return "连接服务器失败";
				} else {
					return "连接服务器超时";
				}
			}
		}) {
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				try {
					final String login_toast = get();
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ConcreteObservable.newInstance().notifyObserver(LoginActivity.class, login_toast, account, password);
						}
					});	
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		if (!mSingleService.isShutdown()) mSingleService.execute(futureTask);
	}
	
	/**
	 * 重新连接
	 * @param reconnectType 0:手动 1：自动
	 */
	public void reconnect(final byte reconnectType) {
		if (isAuthenticated()) return;
		FutureTask<Boolean> futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				// TODO Auto-generated method stub
				try {
					LogUtil.e("重新登陆", "重新登陆");
					mXMPPConnection.disconnect();
					mXMPPConnection.connect();
					if (!mXMPPConnection.isAuthenticated())
						mXMPPConnection.login(SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""),
								SharedPreferenceUtil.getPrefString(mContext, Constants.UPASSWD, ""), Constants.DEFAULT_BINDSOURCE);
					registerAllListener();
					setStatusFromConfig();
					return true;
				} catch (XMPPException e) {
					LogUtil.e(""+e.getMessage()+"---"+e.getLocalizedMessage());
					return false;
				}
			}
		}) {
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				try {
					LogUtil.e("重新登陆", "重新登陆成功");
					if (reconnectType == Constants.RECONNECT_BYHAND)
						ConcreteObservable.newInstance().notifyObserver(ConflictActivity.class, Constants.IS_RECONNECT);
					fetchRoomItem();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		if (!mSingleService.isShutdown()) mSingleService.execute(futureTask);
	}
	
	// 设置登录后的状态
	public void setStatusFromConfig() {
		try {
			String statusMode = SharedPreferenceUtil.getPrefString(mContext,
					Constants.STATUS_MODE, Constants.AVAILABLE);
			Presence presence = null;
			if (statusMode.equals(Constants.AVAILABLE)) {
				presence = new Presence(Presence.Type.available);
				mXMPPConnection.sendPacket(presence);
				return;
			}
			presence = new Presence(Presence.Type.available);
			presence.setMode(Presence.Mode.xa);
			mXMPPConnection.sendPacket(presence);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 注册一些监听 (离线消息、新消息、好友请求、好友上下线等)
	public void registerAllListener() {
		if (isAuthenticated()) {
			registerReconnect();
			registerMessageListener();
			registerPresenceListener();
		}
	}

	/**
	 * 注销
	 * @param logoutType 0: 手动， 1 ：异地登录
	 */
	public void logout(final byte logoutType) {
		FutureTask<Boolean>  futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				// TODO Auto-generated method stub
				SharedPreferenceUtil.setPrefBoolean(mContext, Constants.IS_LOGOUT, true);
				try {
					for (MultiUserChat userChat : mUserChatList) {
						if (userChat != null) userChat.leave();
					}
					mUserChatList.clear();
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					mXMPPConnection.removePacketListener(mMessageListener);
					mXMPPConnection.removePacketListener(mPresenceListener);
					instance = null;
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.e("logout---", e.getMessage()+"---");
					return false;
				}
				if (mXMPPConnection.isConnected()) {
					Presence presence = new Presence(Presence.Type.unavailable);
					mXMPPConnection.disconnect(presence);
					instance = null;
				}
				return true;
			}
		}) {
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				try {
					final boolean isLogout = get();
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (isLogout && logoutType == Constants.LOGOUT_BYHAND) 
								ConcreteObservable.newInstance().notifyObserver(SettingFragment.class, Constants.IS_LOGOUT);
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		if (!mSingleService.isShutdown()) mSingleService.execute(futureTask);
	}

	//判断用户是否登录成功
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		if (mXMPPConnection != null)
			return (mXMPPConnection.isConnected() && mXMPPConnection.isAuthenticated());
		return false;
	}
	
	/* ------------------ 注册、登录、注销 --------------------------*/
	
	/*------------------------- 好友管理 -----------------------*/
	/**
	 * 好友上下线
	 */
	private void registerPresenceListener() {
		if (mPresenceListener != null)
			mXMPPConnection.removePacketListener(mPresenceListener);
		PacketTypeFilter filter = new PacketTypeFilter(Presence.class);
		mPresenceListener = new PacketListener() {
			public void processPacket(Packet packet) {
				try {
					if (packet instanceof Presence) {  
						final Presence presence = (Presence) packet;
						if ((presence.getType().equals(Presence.Type.unavailable) || presence.getType().equals(Presence.Type.available)) &&
								!packet.getFrom().split("@")[0].equals(SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""))
								&& SharedPreferenceUtil.getPrefBoolean(mContext, Constants.VOICE, true))
							MediaPlayer.create(mContext, R.raw.ptt_sendover).start();
						if (presence.getType().equals(Presence.Type.subscribe)) {//好友申请
						} else if (presence.getType().equals(Presence.Type.subscribed)) {//同意添加好友
						} else if (presence.getType().equals(Presence.Type.unsubscribe)) {//拒绝添加好友
						} else if (presence.getType().equals(Presence.Type.unsubscribed)) { //删除好友
						} else if (presence.getType().equals(Presence.Type.unavailable)) {//好友下线
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									ConcreteObservable.newInstance().notifyObserver(MainActivity.class,
											Constants.FRIEND_STATUS, presence.getFrom().split("/")[0], Constants.UNAVAILABLE);
								}
							});
						} else if (presence.getType().equals(Presence.Type.available)){//好友上线
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									ConcreteObservable.newInstance().notifyObserver(MainActivity.class,
											Constants.FRIEND_STATUS, presence.getFrom().split("/")[0], 
												presence.getMode() != null && presence.getMode().toString().equals(Constants.XA) ? Constants.UNAVAILABLE : Constants.AVAILABLE);
								}
							});
						}  
					}
				} catch (Exception e) {
					LogUtil.e("catch presenceListener exception:"+e.getMessage()+"---"+e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		};
		mXMPPConnection.addPacketListener(mPresenceListener, filter);
	}
	
	//注册重连事件
	public void registerReconnect() {
		if (mXMPPConnection == null) return;
		mXMPPConnection.addConnectionListener(new ConnectionListener(){

            @Override
            public void connectionClosed() {
                LogUtil.e("来自连接监听, conn正常关闭");
            }
            
            @Override
            public void connectionClosedOnError(Exception arg0) {
                 //这里就是网络不正常或者被挤掉断线激发的事件
                if(arg0.getMessage().contains("conflict")) {
                	mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							logout(Constants.LOGOUT_BYCONFLICT);
							ConcreteObservable.newInstance().notifyObserver(EmergencyService.class, Constants.ACCOUNT_CONFLICT);
						}
					});
                } else if(arg0.getMessage().contains("Connection timed out")){//连接超时
                    // 不做任何操作，会实现自动重连
                	ToastUtil.showShort(mContext, "连接超时");
                }
            }
            @Override
            public void reconnectingIn(int arg0) {
                 //重新连接的动作正在进行的动作，里面的参数arg0是一个倒计时的数字，如果连接失败的次数增多，数字会越来越大，开始的时候是9
                LogUtil.e("来自连接监听,conn重连中..."+arg0);
            }

            @Override
            public void reconnectionFailed(Exception arg0) {
                 //重新连接失败
            	LogUtil.e("来自连接监听,conn失败："+arg0.getMessage());
            }
            @Override
            public void reconnectionSuccessful() {
                //当网络断线了，重新连接上服务器触发的事件
            	LogUtil.e("来自连接监听,conn重连成功");
            }

        });
	}
	
	// 获取好友列表
	public void fetchFriends() {
		try {
			if (!mXMPPConnection.isAuthenticated()) 
				mXMPPConnection.login(SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""), 
						SharedPreferenceUtil.getPrefString(mContext, Constants.UPASSWD, ""));
			Roster roster = mXMPPConnection.getRoster();
			Collection<RosterGroup> entriesGroup = roster.getGroups();
			
			List<RosterGroup> grouplist = new ArrayList<RosterGroup>();  
	        Collection<RosterGroup> rosterGroup = roster.getGroups();  
	        Iterator<RosterGroup> q = rosterGroup.iterator();  
	        while (q.hasNext()) {  
	            grouplist.add(q.next());  
	        }
			//有分组的情况
			if (entriesGroup.size() > 0) {
				mRosterOffline.deleteAll();
				List<Map<String, String>> rosters = new ArrayList<Map<String,String>>();
				for (RosterGroup group : entriesGroup) {
					Collection<RosterEntry> entries = group.getEntries();
					for (RosterEntry entry : entries) {
						Map<String, String> map = new HashMap<String, String>();
						Iterator<Presence> iterator = mXMPPConnection.getRoster().getPresences(entry.getUser());
						Presence presence = null;
						int i = 0;
						while (iterator.hasNext()) {
							presence = iterator.next();
							i++;
						}
						LogUtil.e("fetch-->"+group.getName()+"--"+entry.getUser()+"---"+entry.getName()+"--"+i+"--"+presence.getFrom()+"----"+entry.getType()+"---"+presence);
						if (entry.getType() == ItemType.both) {
							map.put(DBHelper.R_ROSTER, entry.getUser());
							map.put(DBHelper.R_NICKNAME, entry.getName());
							map.put(DBHelper.R_MODE, presence+"");
							map.put(DBHelper.R_GROUP_NAME, group.getName());
							map.put(DBHelper.R_GROUP_POS, group.getName().substring(0,2));
							rosters.add(map);
						}
					}
					mRosterOffline.insert(rosters);
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("catch fetchFriend():"+e.getMessage()+"---"+e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取房间列表
	 */
	public void fetchRoomItem() {
		FutureTask<List<Map<String, String>>> futureTask = new FutureTask<List<Map<String, String>>>(new Callable<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> call() throws Exception {
				// TODO Auto-generated method stub
				List<Map<String, String>> tmpItems = new ArrayList<Map<String, String>>();
				ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(mXMPPConnection);
				try {
					DiscoverItems discoItems = discoManager.discoverItems("conference."+Constants.DEFAULT_SERVERNAME);
					Iterator<DiscoverItems.Item> iterator = discoItems.getItems();
					int index = 0;
					while (iterator.hasNext()) {
						LogUtil.e("hhhhhhh", index +"");
						index ++;
						DiscoverItems.Item item =  iterator.next();
						try {
							MultiUserChat userChat = new MultiUserChat(mXMPPConnection, item.getEntityID());
							if(!userChat.isJoined()){
								userChat.join(SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, "test"));
							}
							mUserChatList.add(userChat);
							Map<String, String> map = new HashMap<String, String>();
							map.put(DBHelper.ROOM_ID, item.getEntityID());
							map.put(DBHelper.ROOM_NAME, item.getName());
							tmpItems.add(map);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					if(!iterator.hasNext()){
						RoomOffline roomOffline = new RoomOffline(mContext);
						roomOffline.deleteAll();
					}
				} catch (XMPPException e) {
					e.printStackTrace();
					LogUtil.e("fetch_room_async", e.getMessage());
				}
				return tmpItems;
			}
		}) {
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				try {
					final List<Map<String, String>> resList = get();
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							ConcreteObservable.newInstance().notifyObserver(MainActivity.class, 
									Constants.FETCH_ROOM_ENDED, resList);
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LogUtil.e("fetch_room", e.getMessage()+"-");
				}
			}
		};
		if (!mSingleService.isShutdown()) mSingleService.execute(futureTask);
	}
    /*-------------------------- 好友管理(end) ---------------------------*/
	
	/*-------------------------- 消息相关(start) ---------------------------*/	
	//发送消息(发送的消息包括文字、图片、音频)
	//注：由于Asmack的bug，使用自带的api传输文件存在问题，所以将图片、音频经过处理从消息传输
	public void sendMessage(Map<String, String> map) {
		try {
			if (!mXMPPConnection.isAuthenticated()) return;
			final Message newMessage = new Message(map.get(DBHelper.C_CHAT_ROSTER),
					map.get(DBHelper.C_MSG_TYPE).equals(Constants.MSG_TYPE_CHAT) ? 
							Message.Type.chat : Message.Type.groupchat);
			LogUtil.e(newMessage.getType().toString());
			newMessage.setBody(map.get(DBHelper.C_MSG));
			newMessage.setProperty("time", map.get(DBHelper.C_MSG_DURA) != null ? map.get(DBHelper.C_MSG_DURA) : "");
			map.put(DBHelper.C_PACKET_ID, newMessage.getPacketID());
			if (ToolUtil.strIsFile(map.get(DBHelper.C_MSG))) 
				map.put(DBHelper.C_MSG, ToolUtil.strToFile(map.get(DBHelper.C_MSG)));
			else if (ToolUtil.strIsVoice(map.get(DBHelper.C_MSG))) 
				map.put(DBHelper.C_MSG, ToolUtil.strToVoice(map.get(DBHelper.C_MSG)));
			mChatOffline.insert(map);
			map.put(DBHelper.C_MSG_STATUS, DBHelper.C_MSG_READED+"");
			if (isAuthenticated()) {
				mXMPPConnection.sendPacket(newMessage);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	// 注册新消息处理监听(消息)
	private void registerMessageListener() {
		if (mMessageListener != null)
			mXMPPConnection.removePacketListener(mMessageListener);
		PacketTypeFilter filter = new PacketTypeFilter(Message.class);
		mMessageListener = new PacketListener() {
			public void processPacket(Packet packet) {
				try {
					if (packet instanceof Message) {
						Message msg = (Message) packet;
						LogUtil.e("!!!!!!!!!!", msg.getFrom()+"--"+msg.getType().toString());
						if (msg.getType().toString().equals(Constants.MSG_TYPE_ERROR)) return;
						if (msg.getType().toString().equals(Constants.MSG_TYPE_GROUPCHAT) && 
								msg.getFrom().split("/")[1].equals(SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""))) return;
						if (msg.getBody() != null) {
							if (SharedPreferenceUtil.getPrefBoolean(mContext, Constants.VIBRATOR, true)) {
								if (mVibrator == null) mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
								mVibrator.vibrate(400);
							}
							if (SharedPreferenceUtil.getPrefBoolean(mContext, Constants.VOICE, true))
								MediaPlayer.create(mContext, R.raw.system).start();
							final Map<String, String> msgMap = encloseMsg(msg);
							String tempStr = msg.getBody();
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
							FileOutputStream outputStream = null;
							try {
								if (ToolUtil.strIsVoice(tempStr)) {
									byte[] bts = ToolUtil.base64ToByte(ToolUtil.strFromVoice(tempStr));
									File file = new File(ToolUtil.getVOICECachePath(mContext)+format.format(new Date())+".amr");
									outputStream = new FileOutputStream(file);
									outputStream.write(bts);
									outputStream.flush();
									outputStream.close();
									msgMap.put(DBHelper.C_MSG_PATH, file.getPath());
								} else if (ToolUtil.strIsFile(tempStr)) {
									byte[] bts = ToolUtil.base64ToByte(ToolUtil.strFromFile(tempStr));
									File file = new File(ToolUtil.getIMGCachePath(mContext)+format.format(new Date())+".tmp");
									Bitmap bitmap = BitmapFactory.decodeByteArray(bts, 0, bts.length);
									FileOutputStream fos = new FileOutputStream(file);
									bitmap.compress(CompressFormat.PNG, 100, fos);
									fos.close();
									msgMap.put(DBHelper.C_MSG_PATH, file.getPath());
								}
							} catch (Exception e) {
								// TODO: handle exception
								LogUtil.e("service catch newMessage:"+e.getMessage()+"---"+e.getLocalizedMessage());
							}
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									boolean isRoom = msgMap.get(DBHelper.C_MSG_TYPE).equals(Constants.MSG_TYPE_GROUPCHAT);
									ConcreteObservable.newInstance().notifyObserver(MainActivity.class, 
											isRoom ? Constants.NEW_MESSAGE_MULTI : Constants.NEW_MESSAGE, msgMap);
									ConcreteObservable.newInstance().notifyObserver(EmergencyService.class,
											isRoom ? Constants.NEW_MESSAGE_MULTI : Constants.NEW_MESSAGE, msgMap);
								}
							});
						}
					} 
				} catch (Exception e) {
					LogUtil.e("catch registerMessageListener():"+e.getMessage()+"---"+e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		};
		mXMPPConnection.addPacketListener(mMessageListener, filter);
	}
	
	//封装接收消息
	public Map<String, String> encloseMsg(Message message) {
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put(DBHelper.C_PACKET_ID, message.getPacketID());
		tempMap.put(DBHelper.C_CUR_ROSTER, SharedPreferenceUtil.getPrefString(mContext, Constants.UNAME, ""));
		tempMap.put(DBHelper.C_CHAT_ROSTER, message.getFrom().split("/")[0]);
		String nickName = "";
		if (message.getType() != null && message.getType().toString().equalsIgnoreCase(Constants.MSG_TYPE_CHAT)) {
			tempMap.put(DBHelper.C_MSG_TYPE, message.getType().toString());
			nickName = mRosterOffline.selectNickName(message.getFrom().split("/")[0]);
		} else {
			tempMap.put(DBHelper.C_MSG_TYPE, Constants.MSG_TYPE_GROUPCHAT);
			nickName = mRoomOffline.selectNickName(message.getFrom().split("/")[0]);
		}
		tempMap.put(DBHelper.C_CHAT_NICKNAME, nickName.equals("") ? message.getFrom().split("@")[0] : nickName);
		DelayInformation info = (DelayInformation) message.getExtension("x", "jabber:x:delay");
		tempMap.put(DBHelper.C_MSG_TIME, info == null ?
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()):
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(info.getStamp()));
		tempMap.put(DBHelper.C_MSG, message.getBody());
		tempMap.put(DBHelper.C_MSG_DURA, message.getProperty("time")+"");
		tempMap.put(DBHelper.C_MSG_FROM, DBHelper.C_MSG_FROM_OTHER + "");
		tempMap.put(DBHelper.C_MSG_STATUS, DBHelper.C_MSG_UNREAD+"");
		return tempMap;
	}

	/*-------------------------- 消息相关(end) ---------------------------*/
	
}
