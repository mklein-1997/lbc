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
                    html += "<img src='https://cdn.motor1.com/images/mgl/qzbeZ/s2/ferrari-488-pista-spider.jpg'>";
                    html += `<h3>${car.make}<h3>
                             <ul>
                                <li>Make: ${car.make}</li>
                                <li>Model: ${car.model}</li>
                                <li>Year: ${car.year}</li>
                                <li>Tracking ID: ${car.trackingId}</li>
                            </ul>`;
                    html += "<button class='button-1'>Copy ID</button>";
                    html += "</div>";
                }

        if (carsInService) {
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