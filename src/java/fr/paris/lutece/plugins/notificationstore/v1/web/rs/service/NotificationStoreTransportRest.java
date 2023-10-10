/*
 * Copyright (c) 2002-2023, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.notificationstore.v1.web.rs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandResult;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.NotificationResult;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.notificationstore.v1.web.service.IHttpTransportProvider;
import fr.paris.lutece.plugins.notificationstore.v1.web.service.INotificationStoreTransportProvider;
import fr.paris.lutece.plugins.notificationstore.web.utils.NotificationStoreUtils;
import fr.paris.lutece.util.http.SecurityUtil;

/**
 * 
 * NotificationStoreTransportRest
 *
 */
public class NotificationStoreTransportRest extends AbstractTransportRest implements INotificationStoreTransportProvider
{
    // REST CONSTANTS
    public static final String PATH_DEMAND_LIST = "list";
    public static final String PATH_DEMAND_STATUS = "status";
    public static final String PATH_NOTIFICATION_LIST = "notification/list";
    public static final String PATH_TYPE_DEMAND = "type";
    public static final String QUERY_PARAM_INDEX = "index";
    public static final String QUERY_PARAM_ID_DEMAND_TYPE = "idDemandType";
    public static final String QUERY_PARAM_NOTIFICATION_TYPE = "notificationType";
    public static final String QUERY_PARAM_CUSTOMER_ID = "customerId";
    public static final String QUERY_PARAM_ID_DEMAND = "idDemand";
    public static final String QUERY_PARAM_LIST_STATUS = "listStatus";
    

    /**
     * Logger
     */
    private static Logger _logger = Logger.getLogger( NotificationStoreTransportRest.class );

    /** URL for NotificationStore REST service */
    private String _strNotificationStoreEndPoint;

    /**
     * Simple Constructor
     */
    public NotificationStoreTransportRest( )
    {
        super( new HttpAccessTransport( ) );
    }

    /**
     * Constructor with IHttpTransportProvider parameter
     *
     * @param httpTransport
     *            the provider to use
     */
    public NotificationStoreTransportRest( final IHttpTransportProvider httpTransport )
    {
        super( httpTransport );

        _strNotificationStoreEndPoint = httpTransport.getApiEndPointUrl( );
    }

    @Override
    public DemandResult getListDemand( String strCustomerId, String strIdDemandType, String strIndex, String strNotificationType ) throws NotificationException
    {
        _logger.debug( "Get list of demand for customer id " + strCustomerId );

        Map<String, String> mapParams = new HashMap<>( );
        if( StringUtils.isNotEmpty( strCustomerId ) )
        {
            mapParams.put( QUERY_PARAM_CUSTOMER_ID, strCustomerId );
        }
        if( StringUtils.isNotEmpty( strIdDemandType ) )
        {
            mapParams.put( QUERY_PARAM_ID_DEMAND_TYPE, strIdDemandType );
        }
        if( StringUtils.isNotEmpty( strIndex ) )
        {
            mapParams.put( QUERY_PARAM_INDEX, strIndex );
        }
        
        if( StringUtils.isNotEmpty( strNotificationType ) )
        {
            mapParams.put( QUERY_PARAM_NOTIFICATION_TYPE, strNotificationType );
        } 
                   
        try 
        {
        	String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + PATH_DEMAND_LIST , mapParams, new HashMap<>( ) );
        	
        	return NotificationStoreUtils.jsonToObject( strResponse, new TypeReference<DemandResult>( ){}  );
        }
        catch (Exception e)
        {
        	_logger.error(e);
        	throw new NotificationException( e.getMessage( ) ) ;
        }
        
        
    }

    @Override
    public DemandResult getListOfDemandByStatus( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strNotificationType ) throws NotificationException
    {
        _logger.debug( "Get list of demand by status for customer id " + strCustomerId );

        Map<String, String> mapParams = new HashMap<>( );
        
        if( StringUtils.isNotEmpty( strCustomerId ) )
        {
            mapParams.put( QUERY_PARAM_CUSTOMER_ID, strCustomerId );
        }
        if( StringUtils.isNotEmpty( strIdDemandType ) )
        {
            mapParams.put( QUERY_PARAM_ID_DEMAND_TYPE, strIdDemandType );
        }
        if( StringUtils.isNotEmpty( strIndex ) )
        {
            mapParams.put( QUERY_PARAM_INDEX, strIndex );
        }
        if( StringUtils.isNotEmpty( strListStatus ) )
        {
            mapParams.put( QUERY_PARAM_LIST_STATUS, strListStatus );
        } 
        if( StringUtils.isNotEmpty( strNotificationType ) )
        {
            mapParams.put( QUERY_PARAM_NOTIFICATION_TYPE, strNotificationType );
        } 
                 
        try
        {
	        String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + PATH_DEMAND_STATUS, mapParams, new HashMap<>( ) );
	        
	        return NotificationStoreUtils.jsonToObject( strResponse, new TypeReference<DemandResult>( ){} );
	    }
	    catch (Exception e)
	    {
	    	_logger.error(e);
	    	throw new NotificationException( e.getMessage( ) ) ;
	    }
    }

    @Override
    public NotificationResult getListNotification( String strCustomerId, String strIdDemand, String strIdDemandType ) throws NotificationException
    {
        _logger.debug( "Get list of notification of demand id " + strIdDemand );

        Map<String, String> mapParams = new HashMap<>( );
        if( StringUtils.isNotEmpty( strCustomerId ) )
        {
            mapParams.put( QUERY_PARAM_CUSTOMER_ID, strCustomerId );
        }
        if( StringUtils.isNotEmpty( strIdDemandType ) )
        {
            mapParams.put( QUERY_PARAM_ID_DEMAND_TYPE, strIdDemandType );
        }
        if( StringUtils.isNotEmpty( strIdDemand ) )
        {
            mapParams.put( QUERY_PARAM_ID_DEMAND, strIdDemand );
        }
                   
        try
        {
	        String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + PATH_NOTIFICATION_LIST, mapParams, new HashMap<>( ) );
	        
	        return NotificationStoreUtils.jsonToObject( strResponse, new TypeReference<NotificationResult>( ){} );
        }
        catch (Exception e)
        {
        	_logger.error(e);
            _logger.error( "LibraryNotificationStore - Error HttpAccessTransport", e );

        	throw new NotificationException( e.getMessage( ) ) ;
        }
    }

    @Override
    public List<DemandType> getDemandTypes( ) throws NotificationException
    {
        _logger.debug( "Get list of demand type " );
     
        try 
        {
        	String json = _httpTransport.doGet( _strNotificationStoreEndPoint + PATH_TYPE_DEMAND, new HashMap<>( ), new HashMap<>( )  );
        	
        	return NotificationStoreUtils.jsonToObject(json, new TypeReference<List<DemandType>>(){} );
        }
        catch (Exception e)
        {
        	_logger.error(e);
        	throw new NotificationException( e.getMessage( ) ) ;
        }
    }

	@Override
	public String deleteNotificationByCuid(String strCustomerId) throws NotificationException {
		 _logger.debug( "Delete all notifications of customer : " + SecurityUtil.logForgingProtect( strCustomerId ) );
	     
	        try 
	        {
	        	return _httpTransport.doDelete( _strNotificationStoreEndPoint + strCustomerId, new HashMap<>( )  );
	        }
	        catch (Exception e)
	        {
	        	_logger.error(e);
	        	throw new NotificationException( e.getMessage( ) ) ;
	        }
	}

}