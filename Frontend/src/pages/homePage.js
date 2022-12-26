import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/inventoryClient";

class HomePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onAllCars', 'onAddCar', 'onServiceCar', 'onDeleteCar', 'onRentCar',
        'onReturnCar', 'onFindCar'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Event handlers
     */
    async mount() {
        document.getElementById('add-car-form').addEventListener('submit', this.onAddCar);
        document.getElementById('service-form').addEventListener('submit', this.onServiceCar);
        document.getElementById('delete-car-form').addEventListener('submit', this.onDeleteCar);
        document.getElementById('rent-car-form').addEventListener('submit', this.onRentCar);
       // document.getElementById('get-available-cars-button').addEventListener('click', this.onGetAvailableCars);
        document.getElementById('return-car-form').addEventListener('submit', this.onReturnCar);
        document.getElementById('find-car-form').addEventListener('submit', this.onFindCar);
        this.client = new InventoryClient();
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onAddCar(event) {
        // Preventing page from refreshing on form submit
        event.preventDefault();
      //  this.dataStore.set("cars", null); // should we keep dataStore?

        let make = document.getElementById("car-make").value;
        let model = document.getElementById("car-model").value;
        let year = document.getElementById("car-year").value;

        const addedCar = await this.client.addCar(make, model, year, this.errorHandler);
      //  this.dataStore.set("cars", addedCar); // should we keep dataStore?

        if (addedCar) {
            this.showMessage(`Added ${addedCar.make}!`);
        } else {
            this.errorHandler("Error adding car! Try again...");
        }
    }

    async onServiceCar(event) {

        event.preventDefault();

        let trackingId = document.getElementById("car-service").value;
        let isAvailable = false;
        let dateRented = null;
        let returnDate = null;

        const updatedAvailability = await this.client.updateAvailabilityStatus(trackingId, isAvailable, dateRented,
            returnDate, this.errorHandler);

        if (updatedAvailability) {
            this.showMessage("Sent to get serviced!"); // does ${updateAvailability.make} work?
        } else {
            this.errorHandler("Error sending to service! Try again...");
        }
    }

    async onDeleteCar(event) {

        event.preventDefault();

        let trackingId = document.getElementById("car-delete").value;

        const removedCar = await this.client.removeCar(trackingId, this.errorHandler);

        if (removedCar) {
            this.showMessage("Removed car!");
        } else {
            this.errorHandler("Error removing car! Try again...");
        }
    }

    async onRentCar(event) {

        event.preventDefault();

        let trackingId = document.getElementById("car-trackingID").value;
        let isAvailable = false;
        let dateRented = document.getElementById("date-rented").value;
        let returnDate = document.getElementById("date-return").value;

        const rentedCar = await this.client.updateAvailabilityStatus(trackingId, isAvailable, dateRented, returnDate,
            this.errorHandler);

        if (rentedCar) {
            this.showMessage("Rented car!");
        } else {
            this.errorHandler("Error renting car! Try again...");
        }
    }

    async onReturnCar(event) {

        event.preventDefault();

        let trackingId = document.getElementById("car-return").value;
        let isAvailable = true;
        let dateRented = null;
        let returnDate = null;

        const returnedCar = await this.client.updateAvailabilityStatus(trackingId, isAvailable, dateRented, returnDate,
            this.errorHandler);

        if (returnedCar) {
            this.showMessage("Car has been returned!");
        } else {
            this.errorHandler("Error returning car! Try again...");
        }

    }

    async onFindCar(event) {

        event.preventDefault();

        let trackingId = document.getElementById("find-car-input").value;

        const carFound = await this.client.getCarStatus(trackingId, this.errorHandler);

        let firstResultArea = document.getElementById("first-details");
        let secondResultArea = document.getElementById("second-details");
        let thirdResultArea = document.getElementById("third-details");

        /*const example = this.dataStore.get("example");*/

        if (carFound) {
            firstResultArea.innerHTML = `
                <div>Make: ${carFound.make}</div>
                <div>Model: ${carFound.model}</div>
                <div>Year: ${carFound.year}</div>
            `;

            secondResultArea.innerHTML = `
                <div>Available: ${carFound.isAvailable}</div>
                <div>Tracking ID: ${carFound.trackingId}</div>
                <div>Date Rented: ${carFound.dateRented}</div>
            `;

            thirdResultArea.innerHTML = `
                <div>Return Date: ${carFound.returnDate}</div>
            `;
        } else {
            this.errorHandler("Error: Could not find car with given ID");
        }

    }

    // belongs on all cars page?
    /*async onAllCars() {
        let result = await this.client.getAllCarsStatus(this.errorHandler);
        this.dataStore.set("allCars", result);
    }*/

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homePage = new HomePage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);