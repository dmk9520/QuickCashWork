package com.example.findmyschool.Activity.Employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyschool.Model.Certificate;
import com.example.findmyschool.Model.Education;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Job;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Employee_Resume_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private WebView webView;
    private Employees employee;
    private TextView upload;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private PDFView idPDFView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_resume);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        employee = Application.getEmployee();
        initView();
        initListener();
        initData();
    }

    private void initData() {
        if (employee.getResume() != null) {
            if (!TextUtils.isEmpty(employee.getResume())) {
                webView.setVisibility(View.GONE);
                idPDFView.setVisibility(View.VISIBLE);
                new RetrivePDFfromUrl().execute(employee.getResume());
            } else {
                webView.setVisibility(View.VISIBLE);
                idPDFView.setVisibility(View.GONE);
                showWebResume();
            }
        } else {
            webView.setVisibility(View.VISIBLE);
            idPDFView.setVisibility(View.GONE);
            showWebResume();
        }
    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            idPDFView.fromStream(inputStream).load();
        }
    }

    private void showWebResume() {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append(String.format("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Resume</title>\n" +
                "<meta charset=UTF-8>\n" +
                "<link rel=\"shortcut icon\" href=https://ssl.gstatic.com/docs/documents/images/kix-favicon6.ico>\n" +
                "<style type=text/css>body{font-family:arial,sans,sans-serif;margin:0}iframe{border:0;frameborder:0;height:100%%;width:100%%}#header,#footer{background:#f0f0f0;padding:10px 10px}#header{border-bottom:1px #ccc solid}#footer{border-top:1px #ccc solid;border-bottom:1px #ccc solid;font-size:13}#contents{margin:6px}.dash{padding:0 6px}</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=contents>\n" +
                "<style type=text/css>@import url('https://themes.googleusercontent.com/fonts/css?kit=xTOoZr6X-i3kNg7pYrzMsnEzyYBuwf3lO_Sc3Mw9RUVbV0WvE1cEyAoIq5yYZlSc');ol{margin:0;padding:0}table td,table th{padding:0}.c26{border-right-style:solid;padding:3.6pt 3.6pt 3.6pt 3.6pt;border-bottom-color:#fff;border-top-width:0;border-right-width:0;border-left-color:#fff;vertical-align:top;border-right-color:#fff;border-left-width:0;border-top-style:solid;border-left-style:solid;border-bottom-width:0;width:176.3pt;border-top-color:#fff;border-bottom-style:solid}.c4{border-right-style:solid;padding:5pt 5pt 5pt 5pt;border-bottom-color:#fff;border-top-width:0;border-right-width:0;border-left-color:#fff;vertical-align:top;border-right-color:#fff;border-left-width:0;border-top-style:solid;border-left-style:solid;border-bottom-width:0;width:327.7pt;border-top-color:#fff;border-bottom-style:solid}.c16{color:#000;font-weight:700;text-decoration:none;vertical-align:baseline;font-size:12pt;font-family:\"Raleway\";font-style:normal}.c7{color:#000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:10pt;font-family:\"Lato\";font-style:normal}.c13{color:#000;font-weight:700;text-decoration:none;vertical-align:baseline;font-size:10pt;font-family:\"Lato\";font-style:normal}.c1{color:#666;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:9pt;font-family:\"Lato\";font-style:normal}.c19{color:#000;font-weight:400;text-decoration:none;vertical-align:baseline;font-size:6pt;font-family:\"Lato\";font-style:normal}.c20{color:#f2511b;font-weight:700;text-decoration:none;vertical-align:baseline;font-size:16pt;font-family:\"Raleway\";font-style:normal}.c6{padding-top:0;padding-bottom:0;line-height:1.0;text-align:left}.c32{padding-top:5pt;padding-bottom:0;line-height:1.15;text-align:left}.c0{padding-top:10pt;padding-bottom:0;line-height:1.0;text-align:left}.c22{padding-top:5pt;padding-bottom:0;line-height:1.0;text-align:left}.c10{color:#d44500;text-decoration:none;vertical-align:baseline;font-style:normal}.c2{padding-top:0;padding-bottom:0;line-height:1.15;text-align:left}.c33{padding-top:3pt;padding-bottom:0;line-height:1.0;text-align:left}.c9{padding-top:4pt;padding-bottom:0;line-height:1.15;text-align:left}.c23{border-spacing:0;border-collapse:collapse;margin:0 auto}.c30{color:#000;text-decoration:none;vertical-align:baseline;font-style:normal}.c3{padding-top:6pt;padding-bottom:0;line-height:1.15;text-align:left}.c14{padding-top:16pt;padding-bottom:0;line-height:1.15;text-align:left}.c28{padding-top:6pt;padding-bottom:0;line-height:1.0;text-align:left}.c18{font-size:9pt;font-family:\"Lato\";font-weight:400}.c24{font-size:14pt;font-family:\"Lato\";font-weight:700}.c8{font-size:10pt;font-family:\"Lato\";font-weight:400}.c5{font-size:11pt;font-family:\"Lato\";font-weight:400}.c31{background-color:#fff;max-width:504pt;padding:36pt 54pt 36pt 54pt}.c35{font-weight:700;font-size:24pt;font-family:\"Raleway\"}.c11{orphans:2;widows:2;height:11pt}.c21{height:auto}.c15{height:auto}.c27{height:auto}.c34{height:auto}.c29{height:auto}.c25{font-size:10pt}.c12{page-break-after:avoid}.c17{height:265pt}.title{padding-top:6pt;color:#000;font-weight:700;font-size:24pt;padding-bottom:0;font-family:\"Raleway\";line-height:1.0;page-break-after:avoid;orphans:2;widows:2;text-align:left}.subtitle{padding-top:3pt;color:#f2511b;font-weight:700;font-size:16pt;padding-bottom:0;font-family:\"Raleway\";line-height:1.0;page-break-after:avoid;orphans:2;widows:2;text-align:left}li{color:#000;font-size:11pt;font-family:\"Lato\"}p{margin:0;color:#000;font-size:11pt;font-family:\"Lato\"}h1{padding-top:4pt;color:#000;font-weight:700;font-size:12pt;padding-bottom:0;font-family:\"Raleway\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h2{padding-top:6pt;color:#000;font-weight:700;font-size:11pt;padding-bottom:0;font-family:\"Lato\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h3{padding-top:6pt;color:#666;font-size:9pt;padding-bottom:0;font-family:\"Lato\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h4{padding-top:8pt;-webkit-text-decoration-skip:none;color:#666;text-decoration:underline;font-size:11pt;padding-bottom:0;line-height:1.15;page-break-after:avoid;text-decoration-skip-ink:none;font-family:\"Trebuchet MS\";orphans:2;widows:2;text-align:left}h5{padding-top:8pt;color:#666;font-size:11pt;padding-bottom:0;font-family:\"Trebuchet MS\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}h6{padding-top:8pt;color:#666;font-size:11pt;padding-bottom:0;font-family:\"Trebuchet MS\";line-height:1.15;page-break-after:avoid;font-style:italic;orphans:2;widows:2;text-align:left}</style>\n" +
                "<p class=\"c2 c29\"><span class=c19></span></p>\n" +
                "<a id=t.b7144d62fc47a2bfcf177a3c3dd72df0e868051e></a>\n" +
                "<a id=t.0></a>\n" +
                "<table class=c23>\n" +
                "            <tbody>\n" +
                "                <tr class=\"c21\">\n" +
                "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                "                        <p class=\"c6 c12 title\" id=\"h.4prkjmzco10w\"><span>%s</span></p>\n" +
                "                        <p class=\"c33 subtitle\" id=\"h.o2iwx3vdck7p\"><span class=\"c20\">%s</span></p>\n" +
                "                    </td>\n" +
                "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n" +
                "                        <p class=\"c6\"><span style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 418.00px; height: 2.67px;\"><img alt=\"\" src=\"https://lh4.googleusercontent.com/j7t3_XjsJ1PHIrgcWuJOWmQ2fFs9q-TT_LNTDfAXGnVu49aapNgutWcfK1k7pPzGtsu9lOvPynvLW07b_KwpVV0ituspJAXOQ_IBZyN087cqGsXawUahO2qDRCQZ8-qq4esAcP7M\" style=\"width: 418.00px; height: 2.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\" title=\"horizontal line\"></span></p>\n" +
                /* "                        <h1 class=\"c3\" id=\"h.lf5wiiqsu4ub\"><span>%s</span></h1>\n" +*/
                "                        <p class=\"c6\"><span class=\"c7\">%s</span></p>\n" +
                "                        <p class=\"c6\"><span class=\"c25\">%s</span></p>\n" +
                "                        <p class=\"c0\"><span class=\"c10 c8\">%s</span></p>\n" +
                "                        <p class=\"c6\"><span class=\"c8 c10\">%s</span></p>\n" +
                "                    </td>\n" +
                "                </tr>", employee.getName(), employee.getInterested(), employee.getCity() + ", " + employee.getState() + ", " + employee.getCountry(), employee.getGender(), employee.getNumber(), employee.getEmail()));

        if (!employee.getSkill().isEmpty()) {
            htmlContent.append(String.format("\n" +
                    "                <tr class=\"c27\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    /*"                        <p class=\"c6\"><span class=\"c24\">ㅡ</span></p>\n" +*/
                    "                        <h1 class=\"c9\" id=\"h.61e3cm1p1fln\"><span class=\"c16\">" + "Skills" + "</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <p class=\"c2\"><span style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 418.00px; height: 2.67px;\"><img alt=\"\" src=\"https://lh3.googleusercontent.com/n8bZfGajkthDbPpbjeiRJ4w7rNUmj1iFxdZKCHUOVnfH9FgHVt5EBo3vOYIIoE3augYQ_DCZJUzdlStyJ5RaldVrSG36sTE0CjIot2qaiJ3YRyr2i87bt9Y9d0ngdseS9PpG0HzM\" style=\"width: 418.00px; height: 2.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\" title=\"horizontal line\"></span></p>\n" +
                    "                        <p class=\"c3\"><span class=\"c7\">%s</span></p>\n" +
                    "                    </td>\n" +
                    "                </tr>", employee.getSkill()));
        }
        if (!employee.getLanguage().isEmpty()) {
            htmlContent.append(String.format("\n" +
                    "                <tr class=\"c27\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    /*"                        <p class=\"c6\"><span class=\"c24\">ㅡ</span></p>\n" +*/
                    "                        <h1 class=\"c9\" id=\"h.61e3cm1p1fln\"><span class=\"c16\">" + "Languages" + "</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <p class=\"c2\"><span style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 418.00px; height: 2.67px;\"><img alt=\"\" src=\"https://lh3.googleusercontent.com/n8bZfGajkthDbPpbjeiRJ4w7rNUmj1iFxdZKCHUOVnfH9FgHVt5EBo3vOYIIoE3augYQ_DCZJUzdlStyJ5RaldVrSG36sTE0CjIot2qaiJ3YRyr2i87bt9Y9d0ngdseS9PpG0HzM\" style=\"width: 418.00px; height: 2.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\" title=\"horizontal line\"></span></p>\n" +
                    "                        <p class=\"c3\"><span class=\"c7\">%s</span></p>\n" +
                    "                    </td>\n" +
                    "                </tr>", employee.getLanguage()));
        }
        if (employee.getEducation().size() != 0) {
            htmlContent.append("\n" +
                    "                <tr class=\"c15\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    /*"                        <p class=\"c6\"><span class=\"c24\">ㅡ</span></p>\n" +*/
                    "                        <h1 class=\"c9\" id=\"h.tk538brb1kdf\"><span class=\"c16\">" + "Education" + "</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n");
            boolean first = true;
            for (Education education : employee.getEducation()) {
                htmlContent.append(String.format("<h2 class=\"%s\" id=\"h.u3uy0857ab2n\"><span class=\"c5\">%s </span><span class=\"c30 c5\">/ %s</span></h2>\n" +
                        "                        <h3 class=\"c2\" id=\"h.re1qtuma0rpm\"><span class=\"c1\">%s</span></h3>\n" +
                        "                        <p class=\"c32\"><span class=\"c7\">%s</span></p>\n", first ? "c3" : "c14", education.getSchoolname(), education.getLevel(), education.getMark(), education.getSdate() + " to " + education.getEdate()));
                first = false;
            }
            htmlContent.append("</td>\n" +
                    "                </tr>");
        }
        if (employee.getCertificate().size() != 0) {
            htmlContent.append("\n" +
                    "                <tr class=\"c15\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    /* "                        <p class=\"c6\"><span class=\"c24\">ㅡ</span></p>\n" +*/
                    "                        <h1 class=\"c9\" id=\"h.tk538brb1kdf\"><span class=\"c16\">" + "Certificate" + "</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n");
            boolean first = true;
            for (Certificate certificate : employee.getCertificate()) {
                htmlContent.append(String.format("<h2 class=\"%s\" id=\"h.u3uy0857ab2n\"><span class=\"c5\">%s </span><span class=\"c30 c5\">/ %s</span></h2>\n" +
                        "                        <p class=\"c32\"><span class=\"c7\">%s</span></p>\n", first ? "c3" : "c14", certificate.getCertiname(), certificate.getCertiinsti(), certificate.getDate()));
                first = false;
            }
            htmlContent.append("</td>\n" +
                    "                </tr>");
        }
        if (employee.getJob().size() != 0) {
            htmlContent.append("\n" +
                    "                <tr class=\"c15\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    /* "                        <p class=\"c6\"><span class=\"c24\">ㅡ</span></p>\n" +*/
                    "                        <h1 class=\"c9\" id=\"h.tk538brb1kdf\"><span class=\"c16\">" + "Experience" + "</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n");
            boolean first = true;
            for (Job job : employee.getJob()) {
                htmlContent.append(String.format("<h2 class=\"%s\" id=\"h.u3uy0857ab2n\"><span class=\"c5\">%s </span></h2>\n" +
                        "                        <h3 class=\"c2\" id=\"h.re1qtuma0rpm\"><span class=\"c1\">%s</span></h3>\n" +
                        "                        <p class=\"c32\"><span class=\"c7\">%s</span></p>\n", first ? "c3" : "c14", job.getCname(), job.getDesignation(), job.getSdate() + " to " + job.getEdate()));
                first = false;
            }
            htmlContent.append("</td>\n" +
                    "                </tr>");
        }
        htmlContent.append("</tbody>\n" +
                "</table>\n" +
                "<p class=\"c2 c11\"><span class=\"c30 c5\"></span></p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null);
    }


    private void initListener() {
        back.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        webView = (WebView) findViewById(R.id.webView);
        upload = (TextView) findViewById(R.id.upload);
        idPDFView = (PDFView) findViewById(R.id.idPDFView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.upload:
                Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                intentPDF.setType("application/pdf");
                intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentPDF, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            uploadFile(uri);
        }
    }

    private void uploadFile(Uri uri) {
        final ProgressDialog progress = new ProgressDialog(Employee_Resume_Activity.this);
        progress.setTitle("");
        progress.setMessage("Upload Image...");
        progress.setCancelable(false);
        progress.show();
        if (uri != null) {
            final StorageReference ref = storageReference.child("Employees").child(employee.getId()).child("resume");
            UploadTask uploadTask = ref.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String stringUri;
                        stringUri = downloadUri.toString();
                        employee.setResume(stringUri);
                        Application.setEmployee(employee);
                        databaseReference.getRoot().child("Employees").child(employee.getId()).child("resume").setValue(stringUri);
                        progress.dismiss();
                        initData();
                    } else {
                        Toast.makeText(Employee_Resume_Activity.this, "task" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}