document.getElementById('login-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token); // Store JWT token
            window.location.href = 'home.html'; // Redirect to home page
        } else {
            document.getElementById('error-message').innerText = 'Invalid username or password';
        }
    } catch (error) {
        console.error('Error:', error);
    }
});
