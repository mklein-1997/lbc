import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import InventoryClient from "../api/inventoryClient";

class HomePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onAddCar', 'onServiceCar', 'onDeleteCar', 'onRentCar',
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
        document.getElementById('return-car-form').addEventListener('submit', this.onReturnCar);
        document.getElementById('find-car-form').addEventListener('submit', this.onFindCar);
        this.client = new InventoryClient();
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onAddCar(event) {

        event.preventDefault();

        let make = document.getElementById("car-make").value;
        let model = document.getElementById("car-model").value;
        let year = document.getElementById("car-year").value;

        const addedCar = await this.client.addCar(make, model, year, this.errorHandler);

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
        let dateRented = "N/A";
        let returnDate = "N/A";

        const updatedAvailability = await this.client.updateAvailabilityStatus(trackingId, isAvailable, dateRented,
            returnDate, this.errorHandler);

        if (updatedAvailability) {
            this.showMessage("Sent to get serviced!");
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
        let dateRented = "N/A";
        let returnDate = "N/A";

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

        let id = document.getElementById("find-car-input").value;
        let result = await this.client.getCarStatus(id, this.errorHandler);
        this.dataStore.set("cars", result);

        let imageResultArea = document.getElementById("image-area");
        let firstResultArea = document.getElementById("first-details");
        let secondResultArea = document.getElementById("second-details");
        let thirdResultArea = document.getElementById("third-details");

        let car = this.dataStore.get("cars");

        let availableImage = "<img class='img-fluid' id='img' src='https://i.pinimg.com/originals/1f/b3/fd/1fb3fd287f851da90e3ec73b10be294a.gif'/>";
        let inServiceImage = "<img class='img-fluid' id='img' src='https://cdn.dribbble.com/users/207059/screenshots/16573461/ms_11.gif'/>";

        if (car) {

            if (car.isAvailable == true) {
                imageResultArea.innerHTML = availableImage;
            }

            if (car.isAvailable == false) {
                if (car.dateRented == "n/a" || car.dateRented == "N/A") {
                   imageResultArea.innerHTML = inServiceImage;
               }
            }

            firstResultArea.innerHTML = `
                <div>Make: ${car.make}</div>
                <div>Model: ${car.model}</div>
                <div>Year: ${car.year}</div>
            `;

            secondResultArea.innerHTML = `
                <div>Available: ${car.isAvailable}</div>
                <div>Tracking ID: ${car.id}</div>
                <div>Date Rented: ${car.dateRented}</div>
            `;

            thirdResultArea.innerHTML = `
                <div>Return Date: ${car.returnDate}</div>
            `;
        } else {
            this.errorHandler("Error: Could not find car with given ID");
        }

    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homePage = new HomePage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);