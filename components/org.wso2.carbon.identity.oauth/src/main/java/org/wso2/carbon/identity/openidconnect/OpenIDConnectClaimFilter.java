/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.openidconnect;

import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.openidconnect.model.RequestedClaim;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This extension can be used to control how claims are filtered based on requested claims in id_token and user info
 * response.
 */
public interface OpenIDConnectClaimFilter {

    /**
     * Filter user claims based on OIDC Scopes defined.
     * <p>
     * Each OIDC Scope defined has a set of permitted claims. First we consider the requested scopes and aggregate
     * the allowed claims for each requested scope if they are defined OIDC Scopes. Then we filter the user claims
     * which belong to the aggregated allowed claims.
     * </p>
     *
     * @param spTenantDomain  Tenant domain of the service provider to which the OAuth app belongs to.
     * @param requestedScopes Request scopes in the OIDC request.
     * @param userClaims      Retrieved claim values of the authenticated user.
     * @return Claim Map after filtering user claims based on defined scopes.
     */
    Map<String, Object> getClaimsFilteredByOIDCScopes(Map<String, Object> userClaims,
                                                      String[] requestedScopes,
                                                      String clientId,
                                                      String spTenantDomain);

    /**
     * Filter list of claims based on OIDC Scopes requested.
     * <p>
     * Each OIDC Scope defined has a set of permitted claims. We consider the requested scopes and aggregate
     * the allowed claims for each requested scope if they are defined as OIDC Scopes.
     * </p>
     *
     * @param requestedScopes Request scopes in the OIDC request.
     * @param spTenantDomain  Tenant domain of the service provider to which the OAuth app belongs to.
     * @return List of claims based on the defined scopes.
     */
    List<String> getClaimsFilteredByOIDCScopes(Set<String> requestedScopes, String spTenantDomain);


    /**
     * Filter user claims based on user consent.
     *
     * @param userClaims
     * @param authenticatedUser
     * @param clientId
     * @param spTenantDomain
     * @return
     */
    default Map<String, Object> getClaimsFilteredByUserConsent(Map<String, Object> userClaims,
                                                               AuthenticatedUser authenticatedUser,
                                                               String clientId,
                                                               String spTenantDomain) {
        return userClaims;
    }

    /**
     * Priority of the Claim Filter. Claims filters will be sorted based on their priority value and by default only
     * the claim filter with the highest priority will be executed.
     *
     * @return priority of the filter.
     */
    int getPriority();

    /**
     * To filter claims requested in the Request Object
     * @param userClaims
     * @param requestParamClaims
     * @return
     */
    Map<String, Object> getClaimsFilteredByEssentialClaims(Map<String, Object> userClaims,
                                                           List<RequestedClaim> requestParamClaims);
}
