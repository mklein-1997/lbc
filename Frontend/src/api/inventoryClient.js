import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the Car RentalCarService.
 */

 export default class InventoryClient extends BaseClass {

    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'addCar', 'removeCar', 'getCarStatus', 'updateAvailabilityStatus',
        'getAllCarsStatus', 'getAvailableCars', 'getCarsInService'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Adds a new car with the given make, model, and year.
     * @param errorCallback - A function to execute if the call fails.
     * @returns - The car data
     */
     async addCar(make, model, year, errorCallback) {
        try {
            const response = await this.client.post(`cars`, {
                "make": make,
                "model": model,
                "year": year
            });
            return response.data;
        } catch (error) {
            this.handleError("addCar", error, errorCallback);
        }
     }

     async removeCar(trackingId, errorCallback) {
        try {
            const response = await this.client.delete(`/cars/${trackingId}`);
            return response.data;
        } catch (error) {
            this.handleError("removeCar", error, errorCallback);
        }
     }

     async getCarStatus(trackingId, errorCallback) {
        try {
            const response = await this.client.get(`/cars/${trackingId}`);
            return response.data;
        } catch (error) {
            this.handleError("getCarStatus", error, errorCallback);
        }
     }

     async getAllCarsStatus(errorCallback) {
        try {
            const response = await this.client.get("/cars/all");
            return response.data;
        } catch (error) {
            this.handleError("getAllCarsStatus", error, errorCallback);
        }
     }

     async getAvailableCars(errorCallback) {
        try {
            const response = await this.client.get("/cars/available");
            return response.data;
        } catch (error) {
            this.handleError("getAvailableCars", error, errorCallback);
        }
     }

     async getCarsInService(errorCallback) {
        try {
            const response = await this.client.get("/cars/service");
            return response.data;
        } catch (error) {
            this.handleError("getCarsInService", error, errorCallback);
        }
     }

     async updateAvailabilityStatus(trackingId, isAvailable, dateRented, returnDate, errorCallback) {
        try {
            const response = await this.client.put(`/cars/${trackingId}`, {
                "id": trackingId,
                "isAvailable": isAvailable,
                "dateRented": dateRented,
                "returnDate": returnDate
            });
            return response.data;
        } catch (error) {
            this.handleError("updateAvailabilityStatus", error, errorCallback);
        }
     }

     /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
     handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
     }

 }