/*
 * Copyright (c) 2002-2024, City of Paris
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import fr.paris.lutece.plugins.grubusiness.business.demand.DemandCategory;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandStatus;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandResult;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.NotificationResult;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.notificationstore.v1.web.service.IHttpTransportProvider;
import fr.paris.lutece.plugins.notificationstore.v1.web.service.INotificationStoreTransportProvider;
import fr.paris.lutece.plugins.notificationstore.web.utils.NotificationStoreConstants;
import fr.paris.lutece.plugins.notificationstore.web.utils.NotificationStoreUtils;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.http.SecurityUtil;

/**
 * 
 * NotificationStoreTransportRest
 *
 */
public class NotificationStoreTransportRest extends AbstractTransportRest implements INotificationStoreTransportProvider
{

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
    public DemandResult getListDemand( String strCustomerId, String strIdDemandType, String strIndex, String strLimitResult, String strNotificationType ) throws NotificationException
    {
        _logger.debug( "Get list of demand for customer id " + strCustomerId );

        Map<String, String> mapParams = new HashMap<>( );
        if ( StringUtils.isNotEmpty( strCustomerId ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_CUSTOMER_ID, strCustomerId );
        }
        if ( StringUtils.isNotEmpty( strIdDemandType ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_ID_DEMAND_TYPE, strIdDemandType );
        }
        if ( StringUtils.isNotEmpty( strIndex ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_INDEX, strIndex );
        }
        
        if ( StringUtils.isNotEmpty( strLimitResult ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_LIMIT, strLimitResult );
        }

        if ( StringUtils.isNotEmpty( strNotificationType ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_NOTIFICATION_TYPE, strNotificationType );
        }

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_LIST, mapParams, new HashMap<>( ) );

            return NotificationStoreUtils.jsonToObject( strResponse, new TypeReference<DemandResult>( )
            {
            } );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }

    }

    @Override
    public DemandResult getListOfDemandByStatus( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strLimitResult,
            String strNotificationType ) throws NotificationException
    {
        _logger.debug( "Get list of demand by status for customer id " + strCustomerId );

        Map<String, String> mapParams = new HashMap<>( );

        if ( StringUtils.isNotEmpty( strCustomerId ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_CUSTOMER_ID, strCustomerId );
        }
        if ( StringUtils.isNotEmpty( strIdDemandType ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_ID_DEMAND_TYPE, strIdDemandType );
        }
        if ( StringUtils.isNotEmpty( strIndex ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_INDEX, strIndex );
        }
        if ( StringUtils.isNotEmpty( strLimitResult ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_LIMIT, strLimitResult );
        }
        if ( StringUtils.isNotEmpty( strListStatus ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_LIST_STATUS, strListStatus );
        }
        if ( StringUtils.isNotEmpty( strNotificationType ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_NOTIFICATION_TYPE, strNotificationType );
        }

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_STATUS, mapParams, new HashMap<>( ) );

            return NotificationStoreUtils.jsonToObject( strResponse, new TypeReference<DemandResult>( )
            {
            } );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public NotificationResult getListNotification( String strCustomerId, String strIdDemand, String strIdDemandType ) throws NotificationException
    {
        _logger.debug( "Get list of notification of demand id " + strIdDemand );

        Map<String, String> mapParams = new HashMap<>( );
        if ( StringUtils.isNotEmpty( strCustomerId ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_CUSTOMER_ID, strCustomerId );
        }
        if ( StringUtils.isNotEmpty( strIdDemandType ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_ID_DEMAND_TYPE, strIdDemandType );
        }
        if ( StringUtils.isNotEmpty( strIdDemand ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_ID_DEMAND, strIdDemand );
        }

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_NOTIFICATION_LIST, mapParams, new HashMap<>( ) );

            return NotificationStoreUtils.jsonToObject( strResponse, new TypeReference<NotificationResult>( )
            {
            } );
        }
        catch( Exception e )
        {
            _logger.error( e );
            _logger.error( "LibraryNotificationStore - Error HttpAccessTransport", e );

            throw new NotificationException( e.getMessage( ) );
        }
    }


    @Override
    public String deleteNotificationByCuid( String strCustomerId ) throws NotificationException
    {
        _logger.debug( "Delete all notifications of customer : " + SecurityUtil.logForgingProtect( strCustomerId ) );

        try
        {
            return _httpTransport.doDelete( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND + strCustomerId, new HashMap<>( ) );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public List<DemandCategory> getCategoriesList( ) throws NotificationException
    {
        _logger.debug( "Get list of categories" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_CATEGORY_LIST, new HashMap<>( ), new HashMap<>( ) );

            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<List<DemandCategory>>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandCategory getCategory( int nCategoryId ) throws NotificationException
    {
        _logger.debug( "Get category by id" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_CATEGORY + nCategoryId , new HashMap<>( ), new HashMap<>( ) );
            
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandCategory>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandCategory createCategory( DemandCategory category ) throws NotificationException
    {
        _logger.debug( "Create category" );

        Map<String, String> mapHeaders = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );
        mapHeaders.put( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        if ( category != null && StringUtils.isNotEmpty( category.getCode( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_CODE, category.getCode( ) );
        }
        if ( category != null && StringUtils.isNotEmpty( category.getLabel( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_LABEL, category.getLabel( ) );
        }
        if ( category != null )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DEFAULT, String.valueOf( category.isDefault( ) ) );
        }

        try
        {
            String strResponse = _httpTransport.doPost( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_CATEGORY,  mapParams, mapHeaders );
            
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandCategory>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandCategory modifyCategory( DemandCategory category ) throws NotificationException
    {
        _logger.debug( "Modify category" );

        Map<String, String> mapHeaders = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );
        mapHeaders.put( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        if ( category != null && StringUtils.isNotEmpty( category.getCode( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_CODE, category.getCode( ) );
        }
        if ( category != null && StringUtils.isNotEmpty( category.getLabel( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_LABEL, category.getLabel( ) );
        }
        if ( category != null )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DEFAULT, String.valueOf( category.isDefault( ) ) );
        }

        try
        {
            String strResponse = _httpTransport.doPut( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_CATEGORY + category.getId( ) , mapParams, mapHeaders );

            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandCategory>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public void deleteCategory( int nCategoryId ) throws NotificationException
    {
        _logger.debug( "Delete category by id" );

        try
        {
            _httpTransport.doDelete( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_CATEGORY + nCategoryId , new HashMap<>( ) );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public List<DemandStatus> getStatusList( ) throws NotificationException
    {
        _logger.debug( "Get list of status" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_STATUS, new HashMap<>( ), new HashMap<>( ) );
                    
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<List<DemandStatus>>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }
    
    @Override
    public ReferenceList getGenericStatusList( ) throws NotificationException
    {
        _logger.debug( "Get list of generic status" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_GENERIC_STATUS , new HashMap<>( ), new HashMap<>( ) );
                    
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<ReferenceList>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandStatus getStatus( int nStatusId ) throws NotificationException
    {
        _logger.debug( "Get status by id" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_STATUS + nStatusId , new HashMap<>( ), new HashMap<>( ) );
            
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandStatus>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandStatus createStatus( DemandStatus status ) throws NotificationException
    {
        _logger.debug( "Create category" );

        try
        {
            String json = NotificationStoreUtils.getMapper( ).writeValueAsString( status );
            
            String strResponse = _httpTransport.doPostJson( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_STATUS, json, new HashMap<>( ) );
            
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandStatus>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandStatus modifyStatus( DemandStatus status ) throws NotificationException
    {
        _logger.debug( "Modify category" );

        try
        {
            String json = NotificationStoreUtils.getMapper( ).writeValueAsString( status );
            String strResponse = _httpTransport.doPutJson( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_STATUS , json, new HashMap<>( ) );

            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandStatus>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public void deleteStatus( int nStatusId ) throws NotificationException
    {
        _logger.debug( "Delete status by id" );

        try
        {
            _httpTransport.doDelete( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_STATUS + nStatusId , new HashMap<>( ) );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }      
    }
    
    @Override
    public List<DemandType> getDemandTypes( ) throws NotificationException
    {
        _logger.debug( "Get list of demand types" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_TYPES, new HashMap<>( ), new HashMap<>( ) );
                    
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<List<DemandType>>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandType getDemandType( int nDemandTypeId ) throws NotificationException
    {
        _logger.debug( "Get demand type by id" );

        try
        {
            String strResponse = _httpTransport.doGet( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_TYPES + nDemandTypeId , new HashMap<>( ), new HashMap<>( ) );
            
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandType>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandType createDemandType( DemandType demandType ) throws NotificationException
    {
        _logger.debug( "Create demand type" );

        Map<String, String> mapHeaders = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );
        mapHeaders.put( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        if ( demandType != null && demandType.getIdDemandType( ) > 0 )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_ID_DEMAND_TYPE,  String.valueOf( demandType.getIdDemandType( ) ));
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getLabel( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_LABEL, demandType.getLabel( )  );
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getUrl( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_URL, demandType.getUrl( ) );
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getAppCode( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_APP_CODE, demandType.getAppCode( ) );
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getCategory( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_CATEGORY, demandType.getCategory( )  );
        }

        try
        {
            String strResponse = _httpTransport.doPost( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_TYPES,  mapParams, mapHeaders );
            
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandType>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public DemandType modifyDemandType( DemandType demandType ) throws NotificationException
    {
        _logger.debug( "Modify demand type" );

        Map<String, String> mapHeaders = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );
        mapHeaders.put( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        
        if ( demandType != null && demandType.getIdDemandType( ) > 0 )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_ID_DEMAND_TYPE,  String.valueOf( demandType.getIdDemandType( ) ));
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getLabel( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_LABEL, demandType.getLabel( )  );
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getUrl( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_URL, demandType.getUrl( ) );
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getAppCode( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_APP_CODE, demandType.getAppCode( ) );
        }
        if ( demandType != null && StringUtils.isNotEmpty( demandType.getCategory( ) ) )
        {
            mapParams.put( NotificationStoreConstants.QUERY_PARAM_DT_CATEGORY, demandType.getCategory( )  );
        }
        
        try
        {
            String strResponse = _httpTransport.doPut( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_TYPES + demandType.getId( ) , mapParams, mapHeaders );
            return NotificationStoreUtils.jsonToObject( getResult( strResponse ), new TypeReference<DemandType>( ){} );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }

    @Override
    public void deleteDemandType( int nDemandTypeId ) throws NotificationException
    {
        _logger.debug( "Delete demand type by id" );

        try
        {
            _httpTransport.doDelete( _strNotificationStoreEndPoint + NotificationStoreConstants.PATH_DEMAND_TYPES + nDemandTypeId , new HashMap<>( ) );
        }
        catch( Exception e )
        {
            _logger.error( e );
            throw new NotificationException( e.getMessage( ) );
        }
    }
    
    /**
     * 
     * @param strResponse
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private String getResult ( String strResponse ) throws JsonProcessingException
    {
        JsonNode strJsonNode = NotificationStoreUtils.getMapper( ).readTree( strResponse );
        return strJsonNode.get( NotificationStoreConstants.JSON_RESULT ).toString( );
    }
}
