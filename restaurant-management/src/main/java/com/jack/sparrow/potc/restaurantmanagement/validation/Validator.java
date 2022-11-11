package com.jack.sparrow.potc.restaurantmanagement.validation;

import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.model.RestaurantUser;
import com.jack.sparrow.potc.restaurantmanagement.request.Request;

public class Validator {

    public static void validateCreateAndGetCartAndGetCartValueAndPlaceOrder(Request<Cart> request){
        validateEntity(request);
        validateContext(request);
        if (request.getEntity().getUserName()==null){
            throw new RestaurantManagementException("userName is null", new Error("userName cannot be null/empty"));
        }
    }

    public static void validateAddAndDeleteCuisines(Request<Cart> request){
        validateEntity(request);
        validateContext(request);
        if (request.getEntity().getUserName()==null){
            throw new RestaurantManagementException("userName is null", new Error("userName cannot be null/empty"));
        }
        if (request.getEntity().getCuisines()==null){
            throw new RestaurantManagementException("cuisines is null", new Error("cuisines cannot be null"));
        }
    }

    public static void validateAddUser(Request<RestaurantUser> request){
        validateContext(request);
        validateEntity(request);
        if (request.getEntity().getUserName()==null){
            throw new RestaurantManagementException("userName is null", new Error("userName cannot be null/empty"));
        }
        if (request.getEntity().getPassword()==null){
            throw new RestaurantManagementException("password is null", new Error("password cannot be null/empty"));
        }
    }

    public static void validateUpdateUseAccess(Request<RestaurantUser> request){
        validateContext(request);
        validateEntity(request);
        if (request.getEntity().getUserName()==null){
            throw new RestaurantManagementException("userName is null", new Error("userName cannot be null/empty"));
        }
    }

    public static void validateAddCuisineToMenuAndUpdateAvailability(Request<Cuisine> request){
        validateContext(request);
        validateEntity(request);
        if (request.getEntity().getCuisineName()==null){
            throw new RestaurantManagementException("cuisineName is null", new Error("cuisineName cannot be null/empty"));
        }
    }

    private static void validateEntity(Request request){
        if (request.getEntity()==null){
            throw new RestaurantManagementException("entity is null", new Error("entity cannot be null"));
        }
    }

    public static void validateContext(Request request){
        if (request.getContext()==null){
            throw new RestaurantManagementException("context is null", new Error("context cannot be null"));
        }
        if (request.getContext().getUsername()==null){
            throw new RestaurantManagementException("userName is null", new Error("user-name cannot be null/empty"));
        }
    }

}
