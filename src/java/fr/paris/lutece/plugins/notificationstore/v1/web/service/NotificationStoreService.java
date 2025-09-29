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

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.demand.DemandCategory;
import fr.paris.lutece.plugins.grubusiness.business.demand.TemporaryStatus;
import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.DemandResult;
import fr.paris.lutece.plugins.grubusiness.business.web.rs.NotificationResult;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;

/**
 * NotificationStoreService
 */
public class NotificationStoreService extends AbstractCacheableService
{

    private static final String SERVICE_NAME = "NotificationStoreCacheService";
    private static final String STATUS_LIST_KEY = "StatusListKey";
    private static final String CATEGORY_LIST_KEY = "CategoryListKey";
    private static final String DEMANDTYPE_LIST_KEY = "DemandTypeListKey";
    private static final String GENERICSTATUS_LIST_KEY = "GenericStatusListKey";
    private static boolean flushCategoryCache = false;
    private static boolean flushDemandTypeCache = false;
    private static boolean flushStatusCache = false;
    
    /** transport provider */
    private INotificationStoreTransportProvider _transportProvider;
    
    /**
     * Simple Constructor
     */
    public NotificationStoreService( )
    {
        super( );
        initCache( );
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
        initCache( );
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
        return getListDemand( strCustomerId, strIdDemandType, strIndex, strLimitResult, strNotificationType, null );
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
    public DemandResult getListDemand( String strCustomerId, String strIdDemandType, String strIndex, String strLimitResult, String strNotificationType, String strDirectionDateOrderBy ) throws NotificationException
    {
        return this._transportProvider.getListDemand( strCustomerId, strIdDemandType, strIndex, strLimitResult, strNotificationType, strDirectionDateOrderBy );
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
     * @param strCategoryCode
     * @return list of demand
     */
    public DemandResult getListOfDemandByStatus( String strCustomerId, String strListStatus, String strIdDemandType, String strIndex, String strLimitResult,
            String strNotificationType, String strCategoryCode ) throws NotificationException
    {
        return this._transportProvider.getListOfDemandByStatus( strCustomerId, strListStatus, strIdDemandType, strIndex, strLimitResult, strNotificationType, strCategoryCode );
    }

    /**
     * List of notification by demand id, customer id and type demand id
     * 
     * @param strCustomerId
     * @param strIdDemand
     * @param strIdDemandType
     * @param strNotificationType
     * @return list of notification
     */
    public NotificationResult getListNotification( String strCustomerId, String strIdDemand, String strIdDemandType, String strNotificationType ) throws NotificationException
    {
        return this._transportProvider.getListNotification( strCustomerId, strIdDemand, strIdDemandType, strNotificationType );
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
     * Get category by id
     * @param nCategoryId
     * @return the category
     * @throws NotificationException 
     */
    public DemandCategory getCategory( int nCategoryId ) throws NotificationException
    {
	List<DemandCategory> list = getCategoriesList ( );
	
	return list.stream( ).filter(e -> nCategoryId == e.getId( ) )
		       .findFirst( ).orElse( null );
    }
    
    /**
     * Create category
     * @param category
     * @return the category created
     * @throws NotificationException 
     */
    public DemandCategory createCategory( DemandCategory category ) throws NotificationException
    {
	DemandCategory dc = this._transportProvider.createCategory( category );
	
	flushCategoryCache = true;
	
        return dc;
    }

    /**
     * Modify category
     * @param category
     * @return the category modified
     * @throws NotificationException 
     */
    public DemandCategory modifyCategory( DemandCategory category  ) throws NotificationException
    {
	DemandCategory dc = this._transportProvider.modifyCategory( category );
	
	flushCategoryCache = true;
	
	return dc;
    }
    
    /**
     * Delete category
     * @param nCategoryId
     * @throws NotificationException 
     */
    public void deleteCategory( int nCategoryId ) throws NotificationException
    {
        this._transportProvider.deleteCategory( nCategoryId );
        
        flushCategoryCache = true;
    }
    
    
    /**
     * Get demand type by id
     * @param nDemandTypeId
     * @return the demand type
     * @throws NotificationException 
     */
    public DemandType getDemandType( int nDemandTypeId ) throws NotificationException
    {
        return getDemandType( String.valueOf ( nDemandTypeId ) );
    }
    
    /**
     * Create demand type
     * @param demand type
     * @return the demand type created
     * @throws NotificationException 
     */
    public DemandType createDemandType( DemandType demandType ) throws NotificationException
    {
	DemandType dt =  this._transportProvider.createDemandType( demandType );
	
	flushDemandTypeCache = true;
	
	return dt;
    }

    /**
     * Modify demand type
     * @param demand type
     * @return the demand type modified
     * @throws NotificationException 
     */
    public DemandType modifyDemandType( DemandType demandType ) throws NotificationException
    {
	DemandType dt =  this._transportProvider.modifyDemandType( demandType );
	
	flushDemandTypeCache = true;
	
	return dt;
    }
    
    /**
     * Delete demand type
     * @param nDemandTypeId
     * @throws NotificationException 
     */
    public void deleteDemandType( int nDemandTypeId ) throws NotificationException
    {
        this._transportProvider.deleteDemandType( nDemandTypeId );
        
        flushDemandTypeCache = true;
    }
    
    
   /**
     * Get status by id
     * @param nStatusId
     * @return the status
     * @throws NotificationException 
     */
    public TemporaryStatus getStatus( int nStatusId ) throws NotificationException
    {
	List<TemporaryStatus> list = getStatusList ( );
	
	return list.stream( ).filter(e -> nStatusId == e.getId( ) )
		       .findFirst( ).orElse( null );
    }
    
    /**
     * Create status
     * @param status
     * @return the status created
     * @throws NotificationException 
     */
    public TemporaryStatus createStatus( TemporaryStatus status ) throws NotificationException
    {
	TemporaryStatus ts =  this._transportProvider.createStatus( status );
	
	flushStatusCache = true;
	
	return ts;
    }

    /**
     * Modify status
     * @param status
     * @return the status modified
     * @throws NotificationException 
     */
    public TemporaryStatus modifyStatus( TemporaryStatus status  ) throws NotificationException
    {
	TemporaryStatus ts =  this._transportProvider.modifyStatus( status );
	
	flushStatusCache = true;
	
	return ts;
    }
    
    /**
     * Delete status
     * @param nStatusId
     * @throws NotificationException 
     */
    public void deleteStatus( int nStatusId ) throws NotificationException
    {
        this._transportProvider.deleteStatus( nStatusId );
        
        flushStatusCache = true;
    }

    /**
     * Re-assign notifications to consolidated identity after a merge
     * 
     * @param oldCustomerId
     * @param newCustomerId
     * @throws NotificationException
     */
    public void reassignNotifications( String oldCustomerId, String newCustomerId ) throws NotificationException
    {
        this._transportProvider.reassignNotifications( oldCustomerId, newCustomerId);
    }
    
    /**
     * get name
     * 
     * @return the name
     */
    public String getName( )
    {
        return SERVICE_NAME;
    }

    /**
     * get demand type data.
     * 
     * 
     * @param strDemandTypeId
     * @return the demand type
     */
    public DemandType getDemandType( String strTypeId )
    {
	if (strTypeId == null ) return null;
	
	List<DemandType> list = getDemandTypes( );

	return list.stream( ).filter(e -> strTypeId.equals (  String.valueOf ( e.getIdDemandType( ) ) ) )
	       .findFirst( ).orElse( null );
    }

    /**
     * Gets list of categories
     * @return list of categories
     */
    public List<DemandCategory> getCategoriesList( ) 
    {
	checkCategoryCache( );
		
	List<DemandCategory>  list =  (List<DemandCategory>) getFromCache( CATEGORY_LIST_KEY );
	
        return (list != null ? list : new  ArrayList<>() ) ;
    }
    
    /**
     * Gets list of status
     * @return list of status
     * @throws NotificationException 
     */
    public List<TemporaryStatus> getStatusList( ) throws NotificationException
    {
	checkStatusCache ( );
	
	List<TemporaryStatus> list = (List<TemporaryStatus>) getFromCache( STATUS_LIST_KEY );
	
        return (list != null ? list : new  ArrayList<>() ) ;
    }
    
    /**
     * Gets list of generic status
     * @return list of generic status
     * @throws NotificationException 
     */
    public ReferenceList getGenericStatusList( ) throws NotificationException
    {
	checkGenericStatusCache ( );
	
	ReferenceList list = (ReferenceList) getFromCache( GENERICSTATUS_LIST_KEY );
        
        return (list != null ? list : new  ReferenceList() ) ;
    }
    
    /**
     * Gets list of demand types
     * @return list of demand types
     */
    public List<DemandType> getDemandTypes( ) 
    {
	checkDemandTypeCache( );
	
	List<DemandType>  list =  (List<DemandType>) getFromCache ( DEMANDTYPE_LIST_KEY );
        
        return (list != null ? list : new  ArrayList<>() ) ;
    }
    
    /**
     * check cache
     * re-init if necessary
     * 
     */
    private void checkStatusCache( )
    {
	if ( isCacheEnable() && !flushStatusCache && getFromCache( STATUS_LIST_KEY ) != null ) return ;
	
	try
	{	    
	    List<TemporaryStatus> lists = this._transportProvider.getStatusList( );
	    putInCache( STATUS_LIST_KEY , lists );
	    
	    flushStatusCache = false;
	} 
	catch ( NotificationException e )
	{
	    AppLogService.error ( "NotificationStore access problem", e );
	}
    }
    
    /**
     * check cache
     * re-init if necessary
     * 
     */
    private void checkDemandTypeCache( )
    {
	if ( isCacheEnable() && !flushDemandTypeCache && getFromCache( DEMANDTYPE_LIST_KEY ) != null ) return ;
	
	try
	{
	    List<DemandType> listd = this._transportProvider.getDemandTypes( );
	    putInCache( DEMANDTYPE_LIST_KEY, listd );
	    
	    flushDemandTypeCache = false;
	} 
	catch ( NotificationException e )
	{
	    AppLogService.error ( "NotificationStore access problem", e );
	}
    }
    
    /**
     * check cache
     * re-init if necessary
     * 
     */
    private void checkCategoryCache( )
    {
	if ( isCacheEnable() && !flushCategoryCache && getFromCache( CATEGORY_LIST_KEY ) != null ) return ;
	
	try
	{
	    List<DemandCategory> listc = this._transportProvider.getCategoriesList( );
	    putInCache( CATEGORY_LIST_KEY, listc );
	    
	    flushCategoryCache = false;
	} 
	catch ( NotificationException e )
	{
	    AppLogService.error ( "NotificationStore access problem", e );
	}
    }
    
    /**
     * check cache
     * re-init if necessary
     * 
     */
    private void checkGenericStatusCache( )
    {
	if ( getFromCache( GENERICSTATUS_LIST_KEY ) != null ) return ;
	
	try
	{	    
	    ReferenceList gsl = this._transportProvider.getGenericStatusList( );
	    putInCache( GENERICSTATUS_LIST_KEY, gsl );
	    
	} 
	catch ( NotificationException e )
	{
	    AppLogService.error ( "NotificationStore access problem", e );
	}
    }
}
