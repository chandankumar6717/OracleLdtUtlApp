/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

/**
 *
 * @author Chandan
 */
public class BuildLdtCommand {

	public String buildLDTdownloadCmd(String compType, String compName, String DBappsPwd, String JDBCconnString) {
		String ldtComand = null;
		// String appl_short_name = null;
		// System.out.println("buildcommand" + compType + compName + DBappsPwd);
		if ("Valueset".equals(compType)) {
			// prepare Unix command for valueset
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y DOWNLOAD $FND_TOP/patch/115/import/afffload.lct " + compName
					+ ".ldt" + " VALUE_SET FLEX_VALUE_SET_NAME=" + compName;

			return ldtComand;

		} else if ("Concurrent Programe".equals(compType)) {
			// get application short name
			String sql = "SELECT fat.application_short_name "
					+ " FROM   apps.fnd_concurrent_programs fcp, apps.fnd_application_vl  fat " + " WHERE  1 = 1 "
					+ " AND    fcp.application_id = fat.application_id " + " AND    fcp.concurrent_program_name = "
					+ "'" + compName + "'";
			JDBCconnection jdbccon = new JDBCconnection();
			jdbccon.connection(JDBCconnString, "apps", DBappsPwd, sql);// "gehbioddbvn01.am.health.ge.com:1539:biodevc"
			String appl_short_name = jdbccon.getappShortName();
                        //
                        JdbcDoaImpl AppShortName = new JdbcDoaImpl();
                        AppShortName.FindAppCode(compName);
                        
			// prepare Unix command for ConcurrentProgram
			System.out.println(appl_short_name);
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y DOWNLOAD $FND_TOP/patch/115/import/afcpprog.lct " + compName
					+ ".ldt" + " PROGRAM APPLICATION_SHORT_NAME=" + appl_short_name + " CONCURRENT_PROGRAM_NAME="
					+ compName;// appl_short_name;

			return ldtComand;

		} else if ("Lookups".equals(compType)) {
			// prepare Unix command for Lookups
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y DOWNLOAD $FND_TOP/patch/115/import/afffload.lct " + compName
					+ ".ldt" + " VALUE_SET FLEX_VALUE_SET_NAME=" + compName;

			return ldtComand;

		} else if ("Alerts".equals(compType)) {
			// get application short name
			String sql = "SELECT fat.application_short_name "
					+ " FROM   apps.alr_alerts alrt, apps.fnd_application_vl  fat " + " WHERE  1 = 1 "
					+ " AND    alrt.application_id = fat.application_id " + " AND alrt.alert_name = " + "'" + compName
					+ "'";
			JDBCconnection jdbccon = new JDBCconnection();
			jdbccon.connection(JDBCconnString, "apps", DBappsPwd, sql);// "gehbioddbvn01.am.health.ge.com:1539:biodevc"
			String appl_short_name = jdbccon.getappShortName();
			// prepare Unix command for Lookups
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y DOWNLOAD $ALR_TOP/patch/115/import/alr.lct " + compName
					+ ".ldt" + " ALR_ALERTS APPLICATION_SHORT_NAME=" + appl_short_name + " ALERT_NAME=" + compName;

			return ldtComand;
		} else if ("Menu".equals(compType)) {
			// prepare Unix command for Menu
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y DOWNLOAD $FND_TOP/patch/115/import/afsload.lct " + compName
					+ ".ldt" + " MENU MENU_NAME=" + compName;

			return ldtComand;

		}

		else {
			return ldtComand;// need to handle exception and show it on message pop-up
		}

	}

	public String buildLDTloaderCmd(String compType, String ldtfileName, String DBappsPwd, String tempdir) {
		String ldtComand = null;
		// String appl_short_name = null;
		if ("Valueset".equals(compType)) {
			// prepare Unix command for valueset
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y UPLOAD $FND_TOP/patch/115/import/afffload.lct " + tempdir
					+ "/" +'"'+ldtfileName+'"' ;

			return ldtComand;

		} else if ("Concurrent Programe".equals(compType)) {
			// prepare Unix command for ConcurrentProgram
			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y UPLOAD $FND_TOP/patch/115/import/afcpprog.lct "
					+ ldtfileName + " - WARNING=YES UPLOAD_MODE=REPLACE CUSTOM_MODE=FORCE";
			System.out.println("cp uplod cmd :" + ldtComand);
			return ldtComand;

		} else if ("Lookups".equals(compType)) {

			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y UPLOAD $FND_TOP/patch/115/import/afcpprog.lct "
					+ ldtfileName + "- WARNING=YES UPLOAD_MODE=REPLACE CUSTOM_MODE=FORCE";

			return ldtComand;

		} else if ("Alerts".equals(compType)) {

			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y UPLOAD $ALR_TOP/patch/115/import/alr.lct " + '"'+ldtfileName+'"'
					+ " CUSTOM_MODE=FORCE";

			return ldtComand;

		} else if ("Menu".equals(compType)) {

			ldtComand = "FNDLOAD apps/" + DBappsPwd + " 0 Y UPLOAD $FND_TOP/patch/115/import/afsload.lct "
					+ '"'+ldtfileName+'"' ;

			return ldtComand;

		} else {
			return ldtComand;// need to handle exception and show it on message
			// pop-up
		}

	}

}
