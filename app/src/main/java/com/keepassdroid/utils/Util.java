/*
 * Copyright 2017 Brian Pellin, Jeremy Jamet / Kunzisoft.
 *     
 * This file is part of KeePass DX.
 *
 *  KeePass DX is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  KeePass DX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with KeePass DX.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.keepassdroid.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import com.android.keepass.R;
import com.keepassdroid.database.exception.SamsungClipboardException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.ClipboardManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Util {
	public static String getClipboard(Context context) {
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		CharSequence csText = clipboard.getText();
		if (csText == null) {
			return "";
		}
		
		return csText.toString();
	}
	
	public static void copyToClipboard(Context context, String text) throws SamsungClipboardException {
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		
		try {
			clipboard.setText(text);
		} catch (NullPointerException e) {
			throw new SamsungClipboardException(e);
		}
	}
	
	public static void gotoUrl(Context context, String url) throws ActivityNotFoundException {
		if ( url != null && url.length() > 0 ) {
			Uri uri = Uri.parse(url);
			context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
		}
	}
	
	public static void gotoUrl(Context context, int resId) throws ActivityNotFoundException {
		gotoUrl(context, context.getString(resId));
	}

	public static String getEditText(Activity act, int resId) {
		TextView te =  (TextView) act.findViewById(resId);
		
		if (te != null) {
			return te.getText().toString();
		} else {
			return "";
		}
	}
	
	public static void setEditText(Activity act, int resId, String str) {
		TextView te =  (TextView) act.findViewById(resId);
		
		if (te != null) {
			te.setText(str);
		}
	}
	
	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[1024];
		int read;
		while ((read = in.read(buf)) != -1) {
			out.write(buf, 0, read);
		}
	}

	public static void colorStatusBar(Activity activity) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(activity.getResources().getColor(R.color.green_dark));
		}
	}
	
}
