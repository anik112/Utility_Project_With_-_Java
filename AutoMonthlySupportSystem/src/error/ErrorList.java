/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author Anik
 */
public class ErrorList {
    
    public String GET_TBL_NM_ERROR="GET_TBL_NM_ERROR: ";
    public String GET_INDX_NM_ERROR="GET_INDX_NM_ERROR: ";
    public String GET_TBLSPACE_INFO="GET_TBLSPACE_INFO: ";
    public String UPDATE_TABLESPACE="UPDATE_TABLESPACE: ";

    public String getGET_TBL_NM_ERROR() {
        return GET_TBL_NM_ERROR;
    }

    public void setGET_TBL_NM_ERROR(String GET_TBL_NM_ERROR) {
        this.GET_TBL_NM_ERROR += GET_TBL_NM_ERROR;
    }
    
    
}
