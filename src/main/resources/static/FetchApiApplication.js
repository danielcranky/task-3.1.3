const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getAllUsers: async () => await fetch('api/users'),
    getRoles: async () => await fetch('api/roles'),
    getAuthorizedUser: async () => await fetch('api/user'),
    getUserById: async (id) => await fetch(`api/user/${id}`),
    addUser: async (user) => await fetch('api/users', {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUserById: async (id, user) => await fetch(`api/${id}`, {method: 'PATCH', headers: userFetchService.head, body: JSON.stringify(user)}),
    removeUserById: async (id) => await fetch(`api/${id}`, {method: 'DELETE', headers: userFetchService.head})
}

getTableAllUsers()
getInfoAuthorizedUser()

async function getTableAllUsers() {
    await userFetchService.getAllUsers()
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                document.querySelector('#tableAllUsers').insertAdjacentHTML('beforeend',
                    `<tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.email}</td>
                            <td>${user.rolesAsString}</td>
                            <td>
                                <button type="button" class="btn btn-info" data-toggle="modal"
                                    data-target="#edit" onclick="loadUserToEditModal(${user.id})">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger" data-toggle="modal"
                                    data-target="#delete" onclick="loadUserToDeleteModal(${user.id})">Delete</button>
                            </td>
                        </tr>`)
            })
        })
}

async function loadUserToEditModal(id) {
    await userFetchService.getUserById(id)
        .then(response => response.json())
        .then(user => {
            $('#idEdit').val(user.id)
            $('#firstnameEdit').val(user.firstName)
            $('#lastnameEdit').val(user.lastName)
            $('#emailEdit').val(user.email)
            $('#passwordEdit').val("")
        })
}

async function loadUserToDeleteModal(id) {
    await userFetchService.getUserById(id)
        .then(response => response.json())
        .then(user => {
            $('#idDelete').val(user.id)
            $('#firstnameDelete').val(user.firstName)
            $('#lastnameDelete').val(user.lastName)
            $('#emailDelete').val(user.email)
            $('#passwordDelete').val("")
        })
}

async function loadRolesToEditForm() {
    await userFetchService.getRoles()
    .then(response => response.json())
    .then(roles => {
        roles.forEach(role => {
            document.querySelector('#listRolesEdit').insertAdjacentHTML('beforeend',
                `<option value="${role.role}">${role.role.substring(5)}</option>`)
        })
    })

}

async function loadRolesToDeleteForm() {
    await userFetchService.getRoles()
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                document.querySelector('#listRolesDelete').insertAdjacentHTML('beforeend',
                    `<option>${role.role.substring(5)}</option>`)
            })
        })

}

async function loadRolesToAddForm() {
    await userFetchService.getRoles()
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                document.querySelector('#listRolesAdd').insertAdjacentHTML('beforeend',
                    `<option>${role.role.substring(5)}</option>`)
            })
        })

}

async function updateUser() {
    let id = document.getElementById('idEdit').value;
    let firstName = document.getElementById('firstnameEdit').value;
    let lastName = document.getElementById('lastnameEdit').value;
    let email = document.getElementById('emailEdit').value;
    let password = document.getElementById('passwordEdit').value;
    let roleArr = Array.from(listRolesEdit.options)
        .filter(option => option.selected)
        .map(option => option.value);
    let role = [];
    for (let i = 0; i < roleArr.length; i++) {
        role.push({id: i + 1, role: roleArr[i] + ""});
    }
    let user = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        roles: role
    };
    await userFetchService.updateUserById(id, user).then(() => {
        document.getElementById('tableAllUsers').innerHTML = ``;
        getTableAllUsers();
        $(".modal").modal("hide");
    })
}

async function deleteUser() {
    let id = document.getElementById('idDelete').value;

    await userFetchService.removeUserById(id).then(() => {
        document.getElementById('tableAllUsers').innerHTML = ``;
        getTableAllUsers();
        $(".modal").modal("hide");
    })
}

async function addNewUser() {
    let firstName = document.getElementById('firstnameAdd').value;
    let lastName = document.getElementById('lastnameAdd').value;
    let email = document.getElementById('emailAdd').value;
    let password = document.getElementById('passwordAdd').value;
    let roleArr = Array.from(listRolesAdd.options)
        .filter(option => option.selected)
        .map(option => option.value);
    let role = [];
    for (let i = 0; i < roleArr.length; i++) {
        role.push({id: i + 1, role: roleArr[i] + ""});
    }
    let user = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        roles: role
    };
    await userFetchService.addUser(user).then(() => {
        document.getElementById('firstnameAdd').value = ``;
        document.getElementById('lastnameAdd').value = ``;
        document.getElementById('emailAdd').value = ``;
        document.getElementById('passwordAdd').value = ``;
        document.getElementById('listRolesAdd').value = ``;
        document.getElementById('tableAllUsers').innerHTML = ``;
        getTableAllUsers();
    })
}

async function getInfoAuthorizedUser() {
    await userFetchService.getAuthorizedUser()
        .then(response => response.json())
        .then(authorizedUser => {
            document.querySelector('#authorizedUser').insertAdjacentHTML('beforeend',
                `<tr>
                        <td>${authorizedUser.id}</td>
                            <td>${authorizedUser.firstName}</td>
                            <td>${authorizedUser.lastName}</td>
                            <td>${authorizedUser.email}</td>
                            <td>${authorizedUser.rolesAsString}</td>
                    </tr>`)
        })

}