import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import InventoryClient from "../api/inventoryClient";

class AvailabilityPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onAvailableCars', 'renderAvailableCars'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Event handlers
     */
    async mount() {
        this.client = new InventoryClient();

        this.dataStore.addChangeListener(this.renderAvailableCars);
        this.onAvailableCars();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderAvailableCars() {
        let resultArea = document.getElementById("result-area");

        const availableCars = this.dataStore.get("availableCars");

        let html = "";
                for (let car of availableCars) {
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

        if (availableCars) {
            resultArea.innerHTML = html;
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onAvailableCars() {
        let result = await this.client.getAvailableCars(this.errorHandler);
        this.dataStore.set("availableCars", result);
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const availabilityPage = new AvailabilityPage();
    availabilityPage.mount();
};

window.addEventListener('DOMContentLoaded', main);