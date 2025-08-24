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
			
			//counters for box columns
			document.getElementById("new-order-count-badge").textContent = summary.newOrders;
			document.getElementById("in-progress-count-badge").textContent = summary.inProgress;
			document.getElementById("completed-count-badge").textContent = summary.completed;
			
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

function editOrder(orderId) {
  fetch(`${BASE_URL}/orders/${orderId}`)
    .then(res => res.json())
    .then(order => {
      document.getElementById("editOrderId").value = order.id;
      document.getElementById("editCustomerName").value = order.customerName;
      document.getElementById("editOrderDetails").value = order.orderDetails;
	//********************* */
	//we no longer need to use the lines below because it is more like a hard code to show the modal when we are now able to jsut have
	//it appear with the data-bs toggle
      //const modal = new bootstrap.Modal(document.getElementById("editOrderModal"));
      //modal.show();
    })
    .catch(err => console.error("Failed to fetch order:", err));
}

function submitOrderEdit(event) {
  event.preventDefault();

  const orderId = document.getElementById("editOrderId").value;
  const updatedOrder = {
    customerName: document.getElementById("editCustomerName").value,
    orderDetails: document.getElementById("editOrderDetails").value,
  };

  fetch(`${BASE_URL}/orders/${orderId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(updatedOrder)
  })
    .then(res => {
      if (res.ok) {
        alert("Order updated successfully!");
        location.reload(); // Optional: or update the DOM manually
      } else {
        alert("Failed to update order.");
      }
    })
    .catch(err => console.error("Update error:", err));
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
				
				//counters for box columns
				document.getElementById("new-order-count-badge").textContent = summary.newOrders;
				document.getElementById("in-progress-count-badge").textContent = summary.inProgress;
				document.getElementById("completed-count-badge").textContent = summary.completed;
			})
			.catch(error => console.error("Error:", error));
	});
});

document.querySelectorAll('[draggable="true"]').forEach(item => {
	item.addEventListener('dragstart', function(event) {
		event.dataTransfer.setData("id", item.getAttribute('data-order-id'));
	});
});




document.addEventListener('dblclick', function (event) {
	const tile = event.target.closest('.order-tile');
	if (!tile) return;

	const orderId = tile.getAttribute('data-order-id');
	if (!orderId) return;

	const modalBody = document.getElementById('orderModalBody');
	modalBody.innerHTML = `<p>Loading details for Order #${orderId}...</p>`;

	const modal = new bootstrap.Modal(document.getElementById('orderModal'));
	modal.show();

	// ✅ Update this URL to your backend order details API
	fetch(`${BASE_URL}/orders/${orderId}`)
	  .then(response => {
	    if (!response.ok) throw new Error('Failed to fetch order details');
	    return response.json();
	  })
	  .then(order => {
	    modalBody.innerHTML = `
		<div style="position: relative; min-height: 200px;">
	      <h5>Order #${order.id}</h5>
	      <p><strong>Customer Name:</strong> ${order.customerName}</p>
	      <p><strong>Email:</strong> ${order.customerEmail}</p>
	      <p><strong>Status:</strong> ${order.status}</p>
	      <p><strong>Order Details:</strong> ${order.orderDetails}</p>
	      <p><strong>Ordered At:</strong> ${new Date(order.orderedAt).toLocaleString()}</p>
		  <!-- Floating edit button -->
		  <button 
		    id="editOrderIcon" 
		    type="button" 
		    class="btn btn-primary rounded-circle"
		    style="position: absolute; bottom: 10px; right: 10px;"
			data-bs-toggle="modal"
			data-bs-target="#editOrderModal">
		    <i class="bi bi-pencil-square"></i>
		  </button>
		</div>
	    `;
		//	*****************
		//We are still using 2 modals but now we have the ability to have the first modal disappear and allow for the edit modal 
		//to be the only one we see. 
		
		//This is done thorugh the use of bootstraps "Toggle between Modals" from the documentation
		//data-bs-toggle="modal"
		//data-vs-target="#editOrderModal">  this one specifies which mdoal we want to appear assuming it has already been defined.
		// Make edit button work
			    document.getElementById('editOrderIcon').addEventListener('click', function () {
			      editOrder(order.id); // reuse your existing function
			    });
				
		})
	  .catch(error => {
	    console.error(error);
	    modalBody.innerHTML = `<p class="text-danger">Unable to load order details.</p>`;
	  });
	  


});


