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
package fr.paris.lutece.plugins.notificationstore.v1.web.service;

import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.demand.DemandStatus;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandResult;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.NotificationResult;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.util.ReferenceList;

/**
 * NotificationStoreService
 */
public class NotificationStoreService
{

    /** transport provider */
    private INotificationStoreTransportProvider _transportProvider;

    /**
     * Simple Constructor
     */
    public NotificationStoreService( )
    {
        super( );
    }

    /**
     * Constructor with IIdentityTransportProvider in parameters
     *
     * @param transportProvider
     *            INotificationStoreTransportProvider
     */
    public NotificationStoreService( final INotificationStoreTransportProvider transportProvider )
    {
        super( );
        this._transportProvider = transportProvider;
    }

    /**
     * setter of transportProvider parameter
     *
     * @param transportProvider
     *            INotificationStoreTransportProvider
     */
    public void setTransportProvider( final INotificationStoreTransportProvider transportProvider )
    {
        this._transportProvider = transportProvider;
    }

    /**
     * List of demand by customer id, type demand id and index
     * 
     * @param strCustomerId
     * @param strIdDemandType
     * @param strIndex
     *            (Not required)
     * @param strLimitResult
     * @param strNotificationType
     *            (Not required)
     * @return list of demand
     */
    public DemandResult getListDemand( String strCustomerId, String strIdDemandType, String strIndex, String strLimitResult, String strNotificationType ) throws NotificationException
    {
        return this._transportProvider.getListDemand( strCustomerId, strIdDemandType, strIndex, strLimitResult, strNotificationType );
    }

    /**
     * List of demand by list of status, customer id, type demand id and index
     * 
     * @param strCustomerId
     * @param strListStatus
     *            (separated by , )
     * @param strIdDemandType
     * @param strIndex
     *            (Not required)
     * @param strLimitResult
     * @param strNotificationType
     *            (Not required)
     * @return list of demand
     */
    public DemandResult getListOfDemandByStatus( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strLimitResult,
            String strNotificationType ) throws NotificationException
    {
        return this._transportProvider.getListOfDemandByStatus( strCustomerId, strListStatus, strIdDemandType, strIndex, strLimitResult, strNotificationType );
    }

    /**
     * List of notification by demand id, customer id and type demand id
     * 
     * @param strCustomerId
     * @param strIdDemand
     * @param strIdDemandType
     * @return list of notification
     */
    public NotificationResult getListNotification( String strCustomerId, String strIdDemand, String strIdDemandType ) throws NotificationException
    {
        return this._transportProvider.getListNotification( strCustomerId, strIdDemand, strIdDemandType );
    }

    /**
     * List of notification by demand id, customer id and type demand id
     * 
     * @param strCustomerId
     * @param strIdDemand
     * @param strIdDemandType
     * @return list of notification
     */
    public String deleteNotificationByCuid( String strCustomerId ) throws NotificationException
    {
        return this._transportProvider.deleteNotificationByCuid( strCustomerId );
    }
    
    /**
     * Gets list of demand types
     * @return list of demand types
     * @throws NotificationException 
     */
    public List<DemandType> getDemandTypes( ) throws NotificationException
    {
        return this._transportProvider.getDemandTypes( );
    }
    
    /**
     * Get demand type by id
     * @param nDemandTypeId
     * @return the demand type
     * @throws NotificationException 
     */
    public DemandType getDemandType( int nDemandTypeId ) throws NotificationException
    {
        return this._transportProvider.getDemandType( nDemandTypeId );
    }
    
    /**
     * Create demand type
     * @param demand type
     * @return the demand type created
     * @throws NotificationException 
     */
    public DemandType createDemandType( DemandType demandType ) throws NotificationException
    {
        return this._transportProvider.createDemandType( demandType );
    }

    /**
     * Modify demand type
     * @param demand type
     * @return the demand type modified
     * @throws NotificationException 
     */
    public DemandType modifyDemandType( DemandType demandType ) throws NotificationException
    {
        return this._transportProvider.modifyDemandType( demandType );
    }
    
    /**
     * Delete demand type
     * @param nDemandTypeId
     * @throws NotificationException 
     */
    public void deleteDemandType( int nDemandTypeId ) throws NotificationException
    {
        this._transportProvider.deleteDemandType( nDemandTypeId );
    }
    
    
    /**
     * Gets list of status
     * @return list of status
     * @throws NotificationException 
     */
    public List<DemandStatus> getStatusList( ) throws NotificationException
    {
        return this._transportProvider.getStatusList( );
    }
    
    /**
     * Gets list of generic status
     * @return list of generic status
     * @throws NotificationException 
     */
    public ReferenceList getGenericStatusList( ) throws NotificationException
    {
        return this._transportProvider.getGenericStatusList( );
    }
    
    /**
     * Get status by id
     * @param nStatusId
     * @return the status
     * @throws NotificationException 
     */
    public DemandStatus getStatus( int nStatusId ) throws NotificationException
    {
        return this._transportProvider.getStatus( nStatusId );
    }
    
    /**
     * Create status
     * @param status
     * @return the status created
     * @throws NotificationException 
     */
    public DemandStatus createStatus( DemandStatus status ) throws NotificationException
    {
        return this._transportProvider.createStatus( status );
    }

    /**
     * Modify status
     * @param status
     * @return the status modified
     * @throws NotificationException 
     */
    public DemandStatus modifyStatus( DemandStatus status  ) throws NotificationException
    {
        return this._transportProvider.modifyStatus( status );
    }
    
    /**
     * Delete status
     * @param nStatusId
     * @throws NotificationException 
     */
    public void deleteStatus( int nStatusId ) throws NotificationException
    {
        this._transportProvider.deleteStatus( nStatusId );
    }

}
