/**
 * Copyright (c) 2012 Partners In Health.  All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 **/
package org.pih.warehouse.api

import grails.converters.JSON
import org.pih.warehouse.core.ActivityCode
import org.pih.warehouse.core.Location
import org.pih.warehouse.core.LocationTypeCode

class InternalLocationApiController {

    def locationService

    def list = {
        String locationId = params?.location?.id ?: session?.warehouse?.id
        Location parentLocation = locationId ? Location.get(locationId) : null
        if (!parentLocation) {
            throw new IllegalArgumentException("Must provide location.id as a request parameter")
        }

        ActivityCode[] activityCodes = params.activityCode ? params.list("activityCode") : null
        List<Location> locations = locationService.getInternalLocations(parentLocation,
                [LocationTypeCode.INTERNAL, LocationTypeCode.BIN_LOCATION] as LocationTypeCode[], activityCodes)
        render([data: locations] as JSON)
    }

    def read = {
        Location location = Location.get(params.id)
        render([data: location] as JSON)
    }

}
