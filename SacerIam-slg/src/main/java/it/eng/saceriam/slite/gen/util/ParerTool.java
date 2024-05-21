/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.slite.gen.util;

import java.io.File;
import java.nio.charset.Charset;

import it.eng.util.SpringLiteTool;

public class ParerTool extends SpringLiteTool {

    private static final String GEN_PACKAGE = "it.eng.saceriam.slite.gen";
    private static final String FORM_PACKAGE = GEN_PACKAGE + ".form";
    private static final String ACTION_PACKAGE = GEN_PACKAGE + ".action";
    private static final String USER_PACKAGE = "it.eng.spagoLite.security";

    public ParerTool(String actionPath, String actionRerPath, String genPackage, String actionPackage,
            String formPackage) {
        super(actionPath, actionRerPath, genPackage, actionPackage, formPackage);
    }

    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        if (args.length == 0) {
            throw new IllegalArgumentException();
        }
        String basedir = args[0];
        for (String arg : args) {
            System.out.println("Parametro passato al main:" + arg);
        }
        System.out.println("Percorso corrente : " + new File(".").getAbsolutePath());

        System.setProperty("file.encoding", "UTF-8");
        java.lang.reflect.Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);
        String actionPath = basedir.replaceAll("\\\\", "/")
                + "/../SacerIam-web/src/main/java/it/eng/saceriam/web/action";

        ParerTool myParerTool = new ParerTool(actionPath, null, GEN_PACKAGE, ACTION_PACKAGE, FORM_PACKAGE);

        System.out.println("Charset.defaultCharset().name() :" + Charset.defaultCharset().name());

        myParerTool.setSrcPath(basedir + "/target/generated-sources/slite");
        myParerTool.setFormPath(basedir + "/src/main/resources/forms");
        myParerTool.setJspPath(basedir + "/../SacerIam-web/src/main/webapp/jsp");
        myParerTool.setUserPackage(USER_PACKAGE);
        myParerTool.run();
    }
}
