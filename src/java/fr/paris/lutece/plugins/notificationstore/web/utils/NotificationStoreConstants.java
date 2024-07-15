package fr.paris.lutece.plugins.notificationstore.web.utils;

/**
 * 
 * NotificationStoreConstants
 *
 */
public class NotificationStoreConstants
{
    /**
     * Private constructor
     */
    private NotificationStoreConstants ( )
    {
        //Do nothing
    }
    
    // REST PATHS
    public static final String PATH_DEMAND = "demands/";
    public static final String PATH_DEMAND_LIST = PATH_DEMAND + "list";
    public static final String PATH_DEMAND_STATUS =  PATH_DEMAND + "status";
    public static final String PATH_NOTIFICATION_LIST = PATH_DEMAND + "notifications";
    public static final String PATH_TYPE_DEMAND = PATH_DEMAND + "demand_types";
    
    public static final String PATH_CATEGORY = "category/";
    public static final String PATH_STATUS = "status/";
    public static final String PATH_GENERIC_STATUS = PATH_STATUS + "generic_status";
    public static final String PATH_DEMAND_TYPES = "demand_types/";

    
    //QUERY PARAMETERS
    public static final String QUERY_PARAM_INDEX = "index";
    public static final String QUERY_PARAM_LIMIT = "limitResult";
    public static final String QUERY_PARAM_ID_DEMAND_TYPE = "idDemandType";
    public static final String QUERY_PARAM_NOTIFICATION_TYPE = "notificationType";
    public static final String QUERY_PARAM_CUSTOMER_ID = "customerId";
    public static final String QUERY_PARAM_ID_DEMAND = "idDemand";
    public static final String QUERY_PARAM_LIST_STATUS = "listStatus";

    //CATEGORY
    public static final String QUERY_PARAM_ID = "id";
    public static final String QUERY_PARAM_CODE = "code";
    public static final String QUERY_PARAM_LABEL = "label";
    
    //DEMAND TYPE
    public static final String QUERY_PARAM_DT_ID_DEMAND_TYPE = "id_demand_type";
    public static final String QUERY_PARAM_DT_LABEL = "label";
    public static final String QUERY_PARAM_DT_URL = "url";
    public static final String QUERY_PARAM_DT_APP_CODE = "app_code";
    public static final String QUERY_PARAM_DT_CATEGORY = "category";
    
    //CONTANTS
    public static final String JSON_RESULT = "result";
}
