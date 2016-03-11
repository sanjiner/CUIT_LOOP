/// <reference path="../jquery-1.8.0.min.js" />
$(document).ready(function () {
    //pageLoading();
    //uploadLoading();
    $("#page_loading").bind("ajaxSend", function () { 
        $(this).show();
    }).ajaxComplete(function () { 
        $(this).hide();
    });
    $(".link").on("click", function () {
        $("#page_loading").show();
    });
    $(".button").bind("ajaxSend", function () { 
        $(this)[0].disabled=true;
    }).ajaxComplete(function () { 
        $(this)[0].disabled = false;
    });
});

//删除所有的空格
function AllTrim(str, is_global) {
    var result;
    result = str.replace(/(^\s+)|(\s+$)/g, "");
    if (is_global.toLowerCase() == "g")
        result = result.replace(/\s/g, "");
    return result;
}
//删除左右的空格
function EndsTrim(str) {
    if (str != NaN && str != null) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    else {
        return "";
    }
}
//删除左边的空格
function LTrim(str) {
    if (str != NaN && str != null) {
        return this.replace(/(^\s*)/g, "");
    }
    else {
        return "";
    }
}
//删除右边的空格
function RTrim(str) {
    if (str != NaN && str != null) {
        return str.replace(/(\s*$)/g, "");
    }
    else {
        return "";
    }
}
jQuery.extend(String.prototype, {
    allTrim: function (is_global) {
        var result;
        result = this.replace(/(^\s+)|(\s+$)/g, "");
        if (is_global.toLowerCase() == "g")
        { result = result.replace(/\s/g, ""); }
        return result;
    },
    endsTrim: function () {
        if (this != NaN && this != null) {
            return this.replace(/(^\s*)|(\s*$)/g, "");
        }
        else {
            return "";
        }
    },
    lTrim: function () {
        if (this != NaN && this != null) {
            return this.replace(/(^\s*)/g, "");
        }
        else {
            return "";
        }
    },
    rTrim: function () {
        if (this != NaN && this != null) {
            return this.replace(/(\s*$)/g, "");
        }
        else {
            return "";
        }
    }
});

function addCSS() {
    $(".list_ht>table>tbody>tr.row").mouseover(function () {
        $(this).addClass("rowmouseover");
    });
    $(".list_ht>table>tbody>tr").mouseout(function () {
        $(this).removeClass("rowmouseover");
    });
}

function Hint() {
    $("input").focus(function () {
        $(this).next().children("span.error").html("");
    });
    $("input").blur(function () {
    });
}
function inputTip(tabID, strTip) {
    $("#" + tabID).focus(function () {
        if ($(this).val() == strTip) {
            $(this).val("");
        }
    });
    $("#" + tabID).blur(function () {
        if (!$("#" + tabID).val()) {
            $(this).val(strTip);
        }
    });
}

/*删除记录*/
function delRecord(id, callBack, kind) {
    if (confirm("确定删除吗？")) {
        $.ajax({
            type: "post",
            dataType: "text",
            url: "/ConsumerCMS/Ashx/DelRecord.ashx",
            data: { 'kind': kind ? kind : $("#txtKind").val(), 'id': id },
            success: function (result) {
                if (result == "true") {
                    callBack(id);
                }
                else {
                    alert('删除失败');
                }
            },
            cache: false,
            error: function (error) {
                alert('删除失败');
            }
        });
    }
}
/*记录排序*/
function sortRecord(id, tid, direction, callBack) {
    if (tid && typeof (typeid) != "undefined") {
        if (tid.endsTrim() != typeid) {
            return;
        }
    }
    $.ajax({
        type: "post",
        dataType: "text",
        url: "/ConsumerCMS/Ashx/SortRecord.ashx",
        data: { 'kind': $("#txtKind").val(), 'id': id, 'tid': tid, 'direction': direction },
        success: function (result) {
            if (result == "true") {
                if (callBack) {
                    callBack(id, direction);
                }
            }
            else {
                alert('操作失败');
            }
        },
        cache: false,
        error: function (error) {
            alert('操作失败');
        }
    });
}
/*记录排序*/
function handleRecord(id, handleaction, callBack) {
    $.ajax({
        type: "post",
        dataType: "text",
        url: "/ConsumerCMS/Ashx/HandleRecord.ashx",
        data: { 'kind': $("#txtKind").val(), 'id': id, 'handleaction': handleaction },
        success: function (result) {
            if (result == "true") {
                if (callBack) {
                    callBack();
                }
            }
            else {
                alert('操作失败');
            }
        },
        cache: false,
        error: function (error) {
            alert('操作失败');
        }
    });
}
/*验证是否存在：用于类别*/
function valiExist_T(name, id, tid, callBack_False, callBack_True) {
    $.ajax({
        type: "post",
        dataType: "text",
        url: "/ConsumerCMS/Ashx/Exist.ashx",
        data: { 'kind': $("#txtKind").val(), 'action': $("#txtAction").val(), 'moduleid': moduleid, 'name': name, 'id': id, 'tid': tid },
        success: function (result) {
            if (result == "true") {
                callBack_True();
            }
            else if (result == "tologin") {
                self.location.href = "/ConsumerCMS/Login.html";
            }
            else {
                if (callBack_False) {
                    callBack_False();
                }
            }
        },
        cache: false,
        error: function (error) {
        }
    });
}
/*验证是否存在：用于一般信息*/
function valiExist(name, id, tid) {
    var remote = {
        type: "post",
        async: false,
        url: "/ConsumerCMS/Ashx/Exist.ashx",
        dataType: "text",
        data: { 'kind': $("#txtKind").val(), 'action': $("#txtAction").val(), 'moduleid': moduleid, 'name': name, 'id': id, 'tid': tid },
        dataFilter: function (msg) {
            if (msg == "true")
                return false;
            else
                return true;
        }
    };
    return remote;
}
/*验证是否存在：用于特征、规格*/
function valiExists(kind, action, name, id, tid) {
    var remote = {
        type: "post",
        async: false,
        url: "/ConsumerCMS/Ashx/Exist.ashx",
        dataType: "text",
        data: { 'kind': kind, 'action': action, 'moduleid': moduleid, 'name': name, 'id': id, 'tid': tid },
        dataFilter: function (msg) {
            if (msg == "true")
                return false;
            else
                return true;
        }
    };
    return remote;
}
/*验证是否存在：用于验证类别下是否有数据*/
function valiExist_Data(tid) {
    var flag = false;
    $.ajax({
        type: "post",
        async: false,
        url: "/ConsumerCMS/Ashx/Exist.ashx",
        dataType: "text",
        data: { 'kind': $("#txtKind").val(), 'action': "ExistData", 'moduleid': moduleid, 'name': "NotRequired", 'id': null, 'tid': tid },
        success: function (msg) {
            if (msg == "true")
                flag = true;
            else
                flag = false;
        },
        cache: false,
        error: function (error) {
        }
    });
    return flag;
}
/*相同内容合并单元格,调用方法：$("#tbSkuList").rowspan(0);传入的参数是对应的列数 */
jQuery.fn.rowspan = function (colIdx) {
    return this.each(function () {
        var that;
        $("tr:visible", this).each(function (row) {
            $("td:eq(" + colIdx + ")", this).filter(":visible").each(function (col) {
                $(this).removeAttr("rowSpan");
            });
            $("td:eq(" + colIdx + ")", this).filter(":hidden").each(function (col) {
                $(this).show();
            });
            $("td:eq(" + colIdx + ")", this).filter(":visible").each(function (col) {
                if (that != null && $(this).html().endsTrim() == $(that).html().endsTrim()) {
                    rowspan = $(that).attr("rowSpan");
                    if (rowspan == undefined) {
                        $(that).attr("rowSpan", 1);
                        rowspan = $(that).attr("rowSpan");
                    }
                    rowspan = Number(rowspan) + 1;
                    $(that).attr("rowSpan", rowspan);
                    $(this).hide();
                } else {
                    that = this;
                }
            });
        });
    });
}
/*页面加载，出现加载图标*/
function pageLoading() {
    $("body").append("<div id=\"page_loading\" class=\"loading_layer\"><img class=\"page_loading\" src=\"/CMSStyles/Image/Main/loading_page.gif\" alt=\"加载中...\" /></div>");
}
/*页面加载，出现加载图标*/
function uploadLoading() {
    $("body").append("<div id=\"upload_loading\" class=\"loading_layer\"><img class=\"upload_loading\" src=\"/CMSStyles/Image/Main/uploading.gif\" alt=\"加载中...\" /></div>");
}
/*页面加载完成，隐藏加载图标*/
function pageLoaded() {
    $("#page_loading").remove();
}
/*页面加载完成，隐藏加载图标*/
function uploadLoaded() {
    $("#upload_loading").remove();
}

function order(obj, orderBy) {
    if (orderBy.endsTrim() != strOrderBy) {
        $(".list_ht thead tr td a").removeClass("current");
        $(obj).addClass("current");
    }
    strOrderBy = orderBy.endsTrim();
    orderFlag = 1 - orderFlag;
    getList();
    if (orderFlag == 0) {
        $(".list_ht thead tr td a").css("background-image", "url(/CMSStyles/Image/Gridview/ico_down.png)");
    }
    else {
        $(".list_ht thead tr td a").css("background-image", "url(/CMSStyles/Image/Gridview/ico_up.png)");
    }
}
function sortControl() {
    if (orderFlag == 1 && strOrderBy == "SequenceNo") {
        $(".up a,.down a").unbind();
        $(".up").attr("class", "up_disable");
        $(".down").attr("class", "down_disable");
    }
    else {
        $(".up_disable").attr("class", "up");
        $(".down_disable").attr("class", "down");
    }
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}