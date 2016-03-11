/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cuit.travelweather.zxing.decoding;

/**
 * This class provides the constants to use when sending an Intent to Barcode
 * Scanner. These strings are effectively API and cannot be changed.
 */
public final class Intents {
	private Intents() {
	}

	public static final class Scan {
		public static final String ACTION = "com.google.zxing.client.android.SCAN";

		public static final String MODE = "SCAN_MODE";
		public static final String SCAN_FORMATS = "SCAN_FORMATS";

		public static final String CHARACTER_SET = "CHARACTER_SET";

		public static final String PRODUCT_MODE = "PRODUCT_MODE";

		public static final String ONE_D_MODE = "ONE_D_MODE";

		public static final String QR_CODE_MODE = "QR_CODE_MODE";

		public static final String DATA_MATRIX_MODE = "DATA_MATRIX_MODE";

		public static final String RESULT = "SCAN_RESULT";

		public static final String RESULT_FORMAT = "SCAN_RESULT_FORMAT";

		public static final String SAVE_HISTORY = "SAVE_HISTORY";

		private Scan() {
		}
	}

	public static final class Encode {
		public static final String ACTION = "com.google.zxing.client.android.ENCODE";

		public static final String DATA = "ENCODE_DATA";

		public static final String TYPE = "ENCODE_TYPE";

		public static final String FORMAT = "ENCODE_FORMAT";

		private Encode() {
		}
	}

	public static final class SearchBookContents {
		public static final String ACTION = "com.google.zxing.client.android.SEARCH_BOOK_CONTENTS";

		public static final String ISBN = "ISBN";

		public static final String QUERY = "QUERY";

		private SearchBookContents() {
		}
	}

	public static final class WifiConnect {
		public static final String ACTION = "com.google.zxing.client.android.WIFI_CONNECT";
		public static final String SSID = "SSID";
		public static final String TYPE = "TYPE";

		public static final String PASSWORD = "PASSWORD";

		private WifiConnect() {
		}
	}

	public static final class Share {
		public static final String ACTION = "com.google.zxing.client.android.SHARE";

		private Share() {
		}
	}
}
