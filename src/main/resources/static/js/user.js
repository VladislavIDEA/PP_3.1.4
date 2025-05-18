document.addEventListener('DOMContentLoaded', function () {
    fetch('/user/profile_user')
        .then(response => response.json())
        .then(user => {
            // Заполняем хедер
            document.getElementById('header-email').textContent = user.email;
            document.getElementById('header-roles').textContent = user.roles.join(', ');

            // Заполняем таблицу
            document.getElementById('userId').textContent = user.id;
            document.getElementById('userFirstName').textContent = user.firstName;
            document.getElementById('userLastName').textContent = user.lastName;
            document.getElementById('userEmail').textContent = user.email;
            document.getElementById('userRoles').textContent = user.roles.join(', ');
        })
        .catch(error => {
            console.error('Ошибка при загрузке данных:', error);
        });
});
