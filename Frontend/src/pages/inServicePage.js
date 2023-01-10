import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import InventoryClient from "../api/inventoryClient";

class InServicePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCarsInService', 'renderCarsInService'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Event handlers
     */
    async mount() {
        this.client = new InventoryClient();

        this.dataStore.addChangeListener(this.renderCarsInService);
        this.onCarsInService();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderCarsInService() {
        let resultArea = document.getElementById("result-area");

        const carsInService = this.dataStore.get("carsInService");

        let html = "";
                for (let car of carsInService) {
                    html += "<div class='content'>";
                    html += "<img src='https://cdn.dribbble.com/users/207059/screenshots/16573461/ms_11.gif'>";
                    html += `<h3>${car.make} ${car.model}<h3>
                             <ul>
                                <li class="font">Make: ${car.make}</li>
                                <li class="font">Model: ${car.model}</li>
                                <li class="font">Year: ${car.year}</li>
                                <li class="font">Tracking ID: ${car.id}</li>
                            </ul>`;
                    html += "</div>";
                }

        if (!carsInService) {
            resultArea.innerHTML = "No cars being serviced";
        } else {
            resultArea.innerHTML = html;
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onCarsInService() {
        let result = await this.client.getCarsInService(this.errorHandler);
        this.dataStore.set("carsInService", result);
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const inServicePage = new InServicePage();
    inServicePage.mount();
};

window.addEventListener('DOMContentLoaded', main);