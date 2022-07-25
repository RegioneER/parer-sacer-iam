/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Iacolucci_M
 */
public class HelpZipProcessor {

    private static final Logger log = LoggerFactory.getLogger(HelpZipProcessor.class);

    private byte[] zipBlob;
    private byte[] docSorgenteHelp = null;
    private String docFileName = null;
    private String helpHtml = null;
    private String imageDirectory = null;

    private HelpZipProcessor() {
    }

    public HelpZipProcessor(byte[] zipBlob) {
        this.zipBlob = zipBlob;
    }

    public byte[] getDocSorgenteHelp() {
        return docSorgenteHelp;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public String getHelpHtml() {
        return helpHtml;
    }

    public String getBase64EncodedHelpHtml() {
        if (helpHtml == null) {
            return null;
        } else {
            return encodeBase64(helpHtml.getBytes());
        }
    }

    public static String getBase64EncodedHelpHtml(String stringaDaEncodare) {
        if (stringaDaEncodare != null) {
            return encodeBase64(stringaDaEncodare.getBytes());
        } else {
            return null;
        }
    }

    /*
     * Attiva il processamento dello zip e la scompattazione di tutto. Torna TRUE se tutto è OK altrimenti FALSE.
     */
    public boolean processa() {
        boolean processOk = false;
        if (estraiDocPrincipali()) {
            processOk = true;
            elaboraImmagini();
        }
        return processOk;
    }

    /*
     * Estrae i files sulla radice .docx e .htm. Prende il primo che trova di tutti e due.
     */
    private boolean estraiDocPrincipali() {
        boolean docPrincipaliOk = false;
        /*
         * Se lo zip è nullo non fa nulla
         */
        if (zipBlob != null) {
            InputStream is = new ByteArrayInputStream(zipBlob);
            ZipInputStream stream = new ZipInputStream(is, Charset.forName("Cp437"));
            // ZipInputStream stream = new ZipInputStream(is,Charset.forName("UTF-8"));
            ZipEntry entry = null;
            try {
                while ((entry = stream.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    if (entry.isDirectory()) {
                        /*
                         * Se trovo una directory che ha piu' di uno slash significa che nello zip tutto il resto era
                         * all'interno di una directory radice quando invece deve essere tutto sulla root (a parte la
                         * directory delle immagini che e' l'unica ammessa.
                         */
                        if (StringUtils.countMatches(entry.getName(), "/") > 1) {
                            docFileName = null;
                            helpHtml = null;
                            break;
                        }
                        imageDirectory = entry.getName();
                    } else {
                        if ((entryName.endsWith(".docx") || entryName.endsWith(".doc")) && docFileName == null) {
                            docFileName = entryName;
                            log.debug("docFileName: " + docFileName + ", size: " + entry.getSize());
                            estraiDocx(stream);
                        } else if (entryName.endsWith(".htm") && helpHtml == null) {
                            log.debug("entryName: " + entry.getName() + ", size: " + entry.getSize());
                            estraiHtm(stream);
                        }
                    }
                }
                if (docFileName != null && helpHtml != null) {
                    docPrincipaliOk = true;
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        } else {
            docPrincipaliOk = true;
        }
        return docPrincipaliOk;
    }

    /*
     * prende tutto lo zip e naviga solo tra le immagini contenute nella directory delle immagini, codifica le immagini
     * in base64 e le include nei tag <IMG> embeddate nell'HTML dell'help
     */
    private boolean elaboraImmagini() {
        boolean immaginiOk = false;

        if (zipBlob != null) {
            InputStream is = new ByteArrayInputStream(zipBlob);
            ZipInputStream stream = new ZipInputStream(is, Charset.forName("Cp437"));

            ZipEntry entry = null;
            try {
                while ((entry = stream.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    if (!entry.isDirectory()) {
                        if (!entryName.endsWith(".docx") && !entryName.endsWith(".htm")) {

                            log.debug("Immagine nello zip: " + entryName);
                            // Processa tutti gli altri files nella directory
                            String base64String = encodeBase64(extractBytesFromImage(stream));
                            encodeImageTag(entryName, base64String);
                        }
                    }
                }
                immaginiOk = true;
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return immaginiOk;
    }

    private void estraiDocx(ZipInputStream stream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[2048];
        for (int readNum; (readNum = stream.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
        }
        docSorgenteHelp = bos.toByteArray();
        bos.close();
    }

    /*
     * Estrae il file HTM dell'Help filtrato da Word e prende solo la parte compresa tra i tag <body></body>
     */
    private void estraiHtm(ZipInputStream stream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[2048];
        for (int readNum; (readNum = stream.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
        }
        helpHtml = new String(bos.toByteArray(), Charset.forName("UTF-8"));
        int inizio = helpHtml.indexOf("<body");
        inizio = helpHtml.indexOf(">", inizio) + 1;
        int fine = helpHtml.indexOf("</body>");
        helpHtml = helpHtml.substring(inizio, fine);

        bos.close();
    }

    private byte[] extractBytesFromImage(ZipInputStream stream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[2048];
        for (int readNum; (readNum = stream.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
        }
        return bos.toByteArray();
    }

    private static String encodeBase64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    private void encodeImageTag(String pathName, String base64Image) {
        String pathEncodato = pathName.replace(" ", "%20");
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(pathName);
        helpHtml = helpHtml.replaceFirst(pathEncodato, "data:" + mimeType + ";base64," + base64Image);
    }

}
