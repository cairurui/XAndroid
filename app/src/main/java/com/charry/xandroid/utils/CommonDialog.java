/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.charry.xandroid.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.charry.xandroid.R;

public final class CommonDialog {

    private static final String TAG = "CommonDialog";

    private CommonDialog() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        return showLoadingDialog(context, context.getResources().getString(R.string.loading));
    }

    public static ProgressDialog showLoadingDialog(Context context, String loadingMsg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
//        if (progressDialog.getWindow() != null) {
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);

        return progressDialog;
    }

}
