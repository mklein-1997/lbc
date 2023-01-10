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
                    html += `<h3>${car.make} ${car.model}<h3>
                             <ul>
                                <li class="font">Make: ${car.make}</li>
                                <li class="font">Model: ${car.model}</li>
                                <li class="font">Year: ${car.year}</li>
                                <li class="font">Tracking ID: ${car.id}</li>
                            </ul>`;
                    html += "</div>";
                }

        if (availableCars) {
            resultArea.innerHTML = html;
        } else {
            resultArea.innerHTML = "No available cars";
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