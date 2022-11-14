package com.example.scoutchallenge.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.apiFormatHelper;
import com.example.scoutchallenge.interfaces.CallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    public static final String COULD_NOT_GET_PATH = "COULD_NOT_GET_PATH";
    public static final String FAILD_TO_UPLOAD = "FAILD_TO_UPLOAD";
    public static final String SUCCESS_UPLOADED = "SUCCESS_UPLOADED";

    private static ApiClient singletonInstance;
    private OkHttpClient client;
    public static Uri contentUri;


    private ApiClient() {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static ApiClient getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new ApiClient();
        }
        return singletonInstance;
    }

    public void perFormeRequest(String root, JSONObject body, CallBack callBack) {

        Request request = getRequest(root.trim(), body);


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("lanRoot", "failureeeeee - > \n" + e.getMessage());
                callBack.onResult(null);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String resp = response.body().string();

                    Log.e("lanRoot", "✉✉️✉️✉️✉️✉️✉️  Response:  ✉✉️✉️✉️✉️✉️✉️ "
                            + apiFormatHelper.formatApi(resp));

                    callBack.onResult(resp);
                } else {
                    callBack.onResult(null);
                }
            }
        });


    }

    public void perFormeMultiTypeRequest(Uri uri, String root, JSONObject body, CallBack callBack) {
        Activity ctx = App.getSharedInstance().mMyActivity;

        if (uri == null) {
            if (callBack != null) {
                callBack.onResult(null);
            }
            return;
        }


        String path = getPathFromUri(ctx, uri);
        if (StringHelper.isNullOrEmpty(path)) {
            callBack.onResult(COULD_NOT_GET_PATH);
            return;
        }


        Request request = getFileRequest(path, root, body);


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onResult(null);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    Log.e("lanRoot", "✉✉️✉️✉️✉️✉️✉️  Response:  ✉✉️✉️✉️✉️✉️✉️ "
                            + apiFormatHelper.formatApi(resp));

                    callBack.onResult(resp);
                } else {
                    callBack.onResult(null);
                }
            }
        });


    }


    public Request getRequest(String root, JSONObject body) {
        String url = D.URL + root;
        Request request;
        Request.Builder builder = new Request.Builder();

        MediaType mediaType = MediaType.parse("application/json");
        if (body != null) {
            Log.e("lanRoot", " \uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11 Request :" + url + "  \uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11 " +
                    apiFormatHelper.formatApi(body));

            RequestBody requestBody = RequestBody.create(body.toString(), mediaType);
            builder.url(url).
                    addHeader("Accept", "*/*")
                    .addHeader("Accept-Language", "en-us")
                    .addHeader("Connection", "keep-alive")
                    .post(requestBody)
                    .build();
            request = builder.build();
        } else {
            mediaType = MediaType.parse("text/plain");

            RequestBody requestBody = RequestBody.create("", mediaType);

            builder.url(url)
                    .addHeader("Accept", "*/*")
                    .addHeader("Accept-Language", "en-us")
                    .addHeader("Connection", "keep-alive")
                    .post(requestBody)

                    .build();
            request = builder.build();
        }
        return request;
    }

    public Request getFileRequest(String path, String root, JSONObject body) {
        String url = D.URL + root;
        Request request;
        Request.Builder builder = new Request.Builder();

        final File imageFile = new File(path);
        Uri uris = Uri.fromFile(imageFile);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uris.toString());
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        String imageName = imageFile.getName();


        MediaType mediaType = MediaType.parse("application/json");
        if (body != null) {
            Log.e("lanRoot", " \uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11 Request :" + url + "  \uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11 " +
                    apiFormatHelper.formatApi(body));


            RequestBody requestBody = RequestBody.create(body.toString(), mediaType);

            RequestBody fullRequestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", imageName,
                            RequestBody.create(imageFile, MediaType.parse(mime)))
                    .addFormDataPart("body", body.toString())
                    .build();


            request = new Request.Builder()
                    .url(D.URL + root)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .addHeader("content-type", "multipart/form-data")
                    .post(fullRequestBody)
                    .build();

        } else {
            builder.url(url)
                    .build();
            request = builder.build();
        }
        return request;
    }

    // get File Path

    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {
        // check here to is it KITKAT or new version
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String selection = null;
        String[] selectionArgs = null;
        // DocumentProvider
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                String fullPath = getPathFromExtSD(split);
                if (fullPath != "") {
                    return fullPath;
                } else {
                    return null;
                }
            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final String id;
                    Cursor cursor = null;
                    try {
                        cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String fileName = cursor.getString(0);
                            String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            if (!TextUtils.isEmpty(path)) {
                                return path;
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));

                                return getDataColumn(context, contentUri, null, null);
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }
                    }

                } else {
                    final String id = DocumentsContract.getDocumentId(uri);
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (contentUri != null) {
                        return getDataColumn(context, contentUri, null, null);
                    }
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            } else if (isGoogleDriveUri(uri)) {
                return getDriveFilePath(uri, context);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            if (isGoogleDriveUri(uri)) {
                return getDriveFilePath(uri, context);
            }
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                return getMediaFilePathForN(uri, context);
            } else {
                return getDataColumn(context, uri, null, null);
            }
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static boolean fileExists(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }

    private static String getPathFromExtSD(String[] pathData) {
        final String type = pathData[0];
        final String relativePath = "/" + pathData[1];
        String fullPath = "";

        if ("primary".equalsIgnoreCase(type)) {
            fullPath = Environment.getExternalStorageDirectory() + relativePath;
            if (fileExists(fullPath)) {
                return fullPath;
            }
        }

        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        return fullPath;
    }

    private static String getDriveFilePath(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    private static String getMediaFilePathForN(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }


    private static String getDataColumn(Context context, Uri uri,
                                        String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

}
