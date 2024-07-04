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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import fr.paris.lutece.plugins.notificationstore.web.utils.NotificationStoreUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * IdentityRestClientService
 */
public final class HttpApiManagerAccessTransport extends HttpAccessTransport
{
    /** The Constant PARAMS_ACCES_TOKEN. */
    private static final String PARAMS_ACCES_TOKEN = "access_token";
    private static final String TYPE_AUTHENTIFICATION_BASIC = "Basic";
    private static final String TYPE_AUTHENTIFICATION_BEARER = "Bearer";

    /** The Constant PARAMS_GRANT_TYPE. */
    private static final String PARAMS_GRANT_TYPE = "grant_type";

    /** The Constant PARAMS_GRANT_TYPE_VALUE. */
    private static final String PARAMS_GRANT_TYPE_VALUE = "client_credentials";

    private static Logger _logger = Logger.getLogger( HttpApiManagerAccessTransport.class );

    /** URL for REST service apiManager */
    private String _strAccessManagerEndPointUrl;
    private String _strAccessManagerCredentials;

    /**
     * setter of apiManagerEndPoint
     * 
     * @param strApiManagerEndPoint
     *            value to use
     */
    public void setAccessManagerEndPointUrl( String strApiManagerEndPoint )
    {
        this._strAccessManagerEndPointUrl = strApiManagerEndPoint;
    }

    /**
     * Sets the API Manager credentials
     * 
     * @param strCredentials
     *            the API Manager credentials
     */
    public void setAccessManagerCredentials( String strCredentials )
    {
        this._strAccessManagerCredentials = strCredentials;
    }

    /**
     * Gets the security token from API Manager
     * 
     * @return the token
     */
    private String getToken( )
    {
        String strToken = StringUtils.EMPTY;

        _logger.debug( "AccessManager Rest Transport getToken with URL_TOKEN property [" + _strAccessManagerEndPointUrl + "]" );

        Map<String, String> mapHeadersRequest = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );

        mapParams.put( PARAMS_GRANT_TYPE, PARAMS_GRANT_TYPE_VALUE );

        mapHeadersRequest.put( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE );
        mapHeadersRequest.put( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE );
        mapHeadersRequest.put( HttpHeaders.AUTHORIZATION, TYPE_AUTHENTIFICATION_BASIC + " " + _strAccessManagerCredentials );

        final Map<String, String> mapHeadersResponse = new HashMap<>( );
        String strOutput = StringUtils.EMPTY;

        try
        {
            strOutput = this._httpClient.doPost( _strAccessManagerEndPointUrl, mapParams, null, null, mapHeadersRequest, mapHeadersResponse );
        }
        catch( Exception e )
        {
            _logger.error( "NotificationStore - Error HttpAccessTransport : " + e.getMessage( ), e );
        }

        JsonNode strResponseApiManagerJsonObject = null;

        try
        {
            strResponseApiManagerJsonObject = NotificationStoreUtils.getMapper( ).readTree( strOutput );

            if ( ( strResponseApiManagerJsonObject != null ) && strResponseApiManagerJsonObject.has( PARAMS_ACCES_TOKEN ) )
            {
                strToken = strResponseApiManagerJsonObject.get( PARAMS_ACCES_TOKEN ).asText( );
            }
        }
        catch( JsonProcessingException e )
        {
            _logger.debug( "ApiManagerRest.getToken invalid response [" + strOutput + "]" );
            _logger.error( "NotificationStore - Error HttpAccessTransport :" + e.getMessage( ), e );
        }

        return strToken;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IdentityStoreException
     */
    @Override
    protected void addAuthentication( Map<String, String> mapHeadersRequest )
    {
        String strToken = getToken( );

        if ( StringUtils.isNotBlank( strToken ) )
        {
            mapHeadersRequest.put( HttpHeaders.AUTHORIZATION, TYPE_AUTHENTIFICATION_BEARER + " " + strToken );
        }
    }
}
