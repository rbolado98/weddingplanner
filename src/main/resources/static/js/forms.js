document.getElementById('purchasedForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Gather form data
    const formData = new FormData(this);

    try {
        // Send POST request to the server
        const response = await fetch(this.action, {
            method: this.method,
            body: formData
        });

        if (response.ok) {
            // Reload the page if the response is successful
            alert('Item Successfully Updated!');
            location.reload(); // Reload the current page
        } else {
            const error = await response.json();
            alert(`Error: ${error.message}`);
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        alert('An error occurred while submitting the form.');
    }
});
document.getElementById('availableForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Gather form data
    const formData = new FormData(this);

    try {
        // Send POST request to the server
        const response = await fetch(this.action, {
            method: this.method,
            body: formData
        });

        if (response.ok) {
            // Reload the page if the response is successful
            alert('Item Successfully Updated!');
            location.reload(); // Reload the current page
        } else {
            const error = await response.json();
            alert(`Error: ${error.message}`);
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        alert('An error occurred while submitting the form.');
    }
});
document.getElementById('inviteForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Gather form data
    const formData = new FormData(this);

    try {
        // Send POST request to the server
        const response = await fetch(this.action, {
            method: this.method,
            body: formData
        });

        if (response.ok) {
            // Reload the page if the response is successful
            alert('Successfully Added!');
            location.reload(); // Reload the current page
        } else {
            const error = await response.json();
            alert(`Error: ${error.message}`);
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        alert('An error occurred while submitting the form.');
    }
});
document.getElementById('deleteForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Gather form data
    const formData = new FormData(this);

    try {
        // Send POST request to the server
        const response = await fetch(this.action, {
            method: this.method,
            body: formData
        });

        if (response.ok) {
            // Reload the page if the response is successful
            alert('Wedding Successfully Deleted!');
            history.back(); // Go back to the previous page
        } else {
            const error = await response.json();
            alert(`Error: ${error.message}`);
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        alert('An error occurred while submitting the form.');
    }
});
document.getElementById('waitlistForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Gather form data
    const formData = new FormData(this);

    try {
        // Send POST request to the server
        const response = await fetch(this.action, {
            method: this.method,
            body: formData
        });

        if (response.ok) {
            // Reload the page if the response is successful
            alert('Successfully Added!');
            location.reload(); // Reload the current page
        } else {
            const error = await response.json();
            alert(`Error: ${error.message}`);
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        alert('An error occurred while submitting the form.');
    }
});
document.getElementById('itemForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Gather form data
    const formData = new FormData(this);

    try {
        // Send POST request to the server
        const response = await fetch(this.action, {
            method: this.method,
            body: formData
        });

        if (response.ok) {
            // Reload the page if the response is successful
            alert('Successfully Added!');
            location.reload(); // Reload the current page
        } else {
            const error = await response.json();
            alert(`Error: ${error.message}`);
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        alert('An error occurred while submitting the form.');
    }
});