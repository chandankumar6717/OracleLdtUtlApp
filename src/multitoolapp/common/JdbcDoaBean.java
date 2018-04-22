/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

import java.io.Serializable;

public class JdbcDoaBean implements Serializable {

    String DbObjectname;
    String DbObjectType;

    public JdbcDoaBean() {

    }

    public JdbcDoaBean(String DbObjectname, String DbObjectType) {
        this.DbObjectname = DbObjectname;
        this.DbObjectType = DbObjectType;

    }

    public String getDbObjectname() {
        return DbObjectname;
    }

    public void setDbObjectname(String DbObjectname) {
        this.DbObjectname = DbObjectname;
    }

    public String getDbObjectType() {
        return DbObjectType;
    }

    public void setDbObjectType(String DbObjectType) {
        this.DbObjectType = DbObjectType;
    }

}
