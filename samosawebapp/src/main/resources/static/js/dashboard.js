src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
const BASE_URL = "/samosawebapp";

document.getElementById("year").innerHTML = (new Date().getFullYear());

function getElapsedTime(orderedAt) {
	const placedAt = new Date(orderedAt)
	const current = new Date();
	const diffMills = current - placedAt;

	const minutes = Math.floor(diffMills / 60000);
	if (minutes < 1) return "Just now";
	if (minutes < 60) return `${minutes} minutes ago`;

	const hours = Math.floor(minutes / 60);
	if (hours < 24) return `${hours} hours ago`;

	const days = Math.floor(hours / 24);
	if (days < 7) return `${days} day${days !== 1 ? 's' : ''} ago`;

	return "More than a week ago";
}
function updateElapsedTimes() {
	document.querySelectorAll('.elapsed-time').forEach(span => {
		const orderedAt = span.getAttribute('data-ordered-at');
		console.log('OrderedAt:', orderedAt);
		span.textContent = getElapsedTime(orderedAt);
	});
}
function updateOrderSummary() {
	fetch(`${BASE_URL}/orders/summary`)
		.then(res => {
			if (!res.ok) throw new Error("Failed to fetch summary");
			return res.json();
		})
		.then(summary => {
			document.getElementById("new-order-count").textContent = summary.newOrders;
			document.getElementById("in-progress-count").textContent = summary.inProgress;
			document.getElementById("completed-count").textContent = summary.completed;
		})
		.catch(error => console.error("Summary update error:", error));
}

function cancelOrder(orderId) {
	if (confirm("Cancel this order?")) {
		fetch(`${BASE_URL}/orders/${orderId}/archive`, { method: 'POST' })
			.then(res => {
				if (res.ok) {
					document.querySelector(`[data-order-id="${orderId}"]`).remove();
					updateOrderSummary();
				} else {
					alert("Failed to cancel.");
				}
			})
			.catch(error => console.error("Cancel error:", error));
	}
}

function archiveOrder(orderId) {
	if (confirm("Archive this order?")) {
		fetch(`${BASE_URL}/orders/${orderId}/archive`, { method: 'POST' })
			.then(res => {
				if (res.ok) {
					document.querySelector(`[data-order-id="${orderId}"]`).remove();
					updateOrderSummary();
				} else {
					alert("Failed to archive.");
				}
			})
			.catch(error => console.error("Archive error:", error));
	}
}

// Run time elapsed calculation once immediately
updateElapsedTimes();

// Then run every minute (60000 ms)
setInterval(updateElapsedTimes, 60000);

document.querySelectorAll('.order-box, #new-orders, #in-progress-orders, #completed-orders').forEach(box => {
	box.addEventListener('dragover', event => event.preventDefault());

	box.addEventListener('drop', event => {
		event.preventDefault();
		const orderId = event.dataTransfer.getData("id");
		const draggedItem = document.querySelector(`[data-order-id='${orderId}']`);
		if (!orderId || !draggedItem) return;

		const newStatus = box.id.toUpperCase().replaceAll("-", " ").replace("ORDERS", "").trim();
		box.appendChild(draggedItem);

		fetch(`${BASE_URL}/orders/${orderId}/status?status=${encodeURIComponent(newStatus)}`, { method: "PUT" })
			.then(res => {
				if (!res.ok) throw new Error("Failed to update order status");
				const button = draggedItem.querySelector("button.dropdown-item");
				if (newStatus === "COMPLETED") {
					button.textContent = "Archive";
					button.onclick = () => archiveOrder(orderId);
				} else {
					button.textContent = "Cancel";
					button.onclick = () => cancelOrder(orderId);
				}
				return res.json();
			})
			.then(() => fetch(`${BASE_URL}/orders/summary`))
			.then(res => {
				if (!res.ok) throw new Error("Failed to fetch summary counts");
				return res.json();
			})
			.then(summary => {
				document.getElementById("new-order-count").textContent = summary.newOrders;
				document.getElementById("in-progress-count").textContent = summary.inProgress;
				document.getElementById("completed-count").textContent = summary.completed;
			})
			.catch(error => console.error("Error:", error));
	});
});

document.querySelectorAll('[draggable="true"]').forEach(item => {
	item.addEventListener('dragstart', function(event) {
		event.dataTransfer.setData("id", item.getAttribute('data-order-id'));
	});
});