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
 * Interface for providing NotificationStore transport.
 */
public interface INotificationStoreTransportProvider
{

    /**
     * List of demand by customer id, type demand id and index
     * 
     * @param strCustomerId
     * @param strIdDemandType
     * @param strIndex
     *            (Not required)
     * @param strLimitResult
     * @param strNotificationType
     * @return list of demand
     */
    DemandResult getListDemand( String strCustomerId, String strIdDemandType, String strIndex, String strLimitResult, String strNotificationType ) throws NotificationException;

    /**
     * List of demand by list of status, customer id, type demand id and index
     * 
     * @param strCustomerId
     * @param strListStatus
     *            (separated by , )
     * @param strIdDemandType
     * @param strNotificationType
     * @param strIndex
     *            (Not required)
     * @param strLimitResult
     * @return list of demand
     */
    DemandResult getListOfDemandByStatus( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strLimitResult, String strNotificationType )
            throws NotificationException;

    /**
     * List of notification by demand id, customer id and type demand id
     * 
     * @param strCustomerId
     * @param strIdDemand
     * @param strIdDemandType
     * @return list of notification
     */
    NotificationResult getListNotification( String strCustomerId, String strIdDemand, String strIdDemandType ) throws NotificationException;

    /**
     * delete all notifications of a customer
     * 
     * @param strCustomerId
     * @return
     * @throws NotificationException
     */
    String deleteNotificationByCuid( String strCustomerId ) throws NotificationException;
    
      
    /**
     * Gets list of demand types
     * @return list of demand types
     * @throws NotificationException
     */
    List<DemandType> getDemandTypes( ) throws NotificationException;
    
    /**
     * Get demand type by id
     * @param nDemandTypeId
     * @return the demand type
     * @throws NotificationException
     */
    DemandType getDemandType( int nDemandTypeId ) throws NotificationException;
    
    /**
     * Create demand type
     * @param demand type
     * @return the demand type created
     * @throws NotificationException
     */
    DemandType createDemandType( DemandType demandType ) throws NotificationException;

    /**
     * Modify demand type
     * @param demand type
     * @return the demand type modified
     * @throws NotificationException
     */
    DemandType modifyDemandType( DemandType demandType  ) throws NotificationException;
    
    /**
     * Delete demand type
     * @param nDemandTypeId
     * @throws NotificationException
     */
    void deleteDemandType( int nDemandTypeId ) throws NotificationException;
    
    
    /**
     * Gets list of status
     * @return list of status
     * @throws NotificationException
     */
    List<DemandStatus> getStatusList( ) throws NotificationException;

    /**
     * Gets list of generic status
     * @return list of generic status
     * @throws NotificationException
     */
    ReferenceList getGenericStatusList( ) throws NotificationException;
    
    /**
     * Get status by id
     * @param nStatusId
     * @return the status
     * @throws NotificationException
     */
    DemandStatus getStatus( int nStatusId ) throws NotificationException;
    
    /**
     * Create status
     * @param status
     * @return the status created
     * @throws NotificationException
     */
    DemandStatus createStatus( DemandStatus status ) throws NotificationException;

    /**
     * Modify status
     * @param status
     * @return the status modified
     * @throws NotificationException
     */
    DemandStatus modifyStatus( DemandStatus status  ) throws NotificationException;
    
    /**
     * Delete status
     * @param nStatusId
     * @throws NotificationException
     */
    void deleteStatus( int nStatusId ) throws NotificationException;

}
