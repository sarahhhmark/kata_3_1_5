const url = 'http://localhost:8080/api/admin/users'
const spanUsername = document.querySelector('#spanUsername')
const spanRoles = document.querySelector('#spanRoles')
const content = document.querySelector('#tableBody')
let result = ''
const modalEdit = new bootstrap.Modal(document.getElementById('modalEdit'))
const modalDelete = new bootstrap.Modal(document.getElementById('modalDelete'))
const editSubmit = document.querySelector('#editSubmit')
const deleteSubmit = document.getElementById('deleteSubmit')
const newUserSubmit = document.getElementById('newUserSubmit')
let ids = document.querySelectorAll('.idUser')
let names = document.querySelectorAll('.nameUser')
let lastnames = document.querySelectorAll('.lastnameUser')
let ages = document.querySelectorAll('.ageUser')
let usernames = document.querySelectorAll('.usernameUser')
let passwords = document.querySelectorAll('.passwordUser')
let roles = document.querySelectorAll('.rolesUser')
let usernamesForValidation = []

// Получаем всех юзеров
function getAllUsers(fillTable = true) {
    fetch(url).then(response => response.json())
        .then(users => {
            if (fillTable) {
                makeRow(users)
            } else {
                usernamesForValidation = users.reduce((acc, user) => {
                    acc[user.id] = user.username;
                    return acc;
                }, {})
            }
        })
        .catch(error => console.log(error))
}

getAllUsers()

// document.addEventListener('DOMContentLoaded', )

// Функция по заполнению таблички юзеров
function makeRow(users) {
    users.forEach(user => {
        let roles = ''
        for (let role of user.roles) {
            roles += role.name.split('_')[1] + ' '
        }
        result += `
    <tr>
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.lastname}</td>
    <td>${user.age}</td>
    <td>${user.username}</td>
    <td hidden="hidden">${user.password}</td>
    <td>${roles}</td>
    <td><a id="btnEdit" class="btn btn-primary">Edit</a></td>
    <td><a id="btnDelete" class="btn btn-danger">Delete</a></td>
    </tr>
    `
    })
    content.innerHTML = result
}

let idForm = 0

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

on(document, 'click', '#btnEdit', e => {
    const filler = e.target.parentNode.parentNode
    fillModal(filler, 1)
    modalEdit.show()

})

on(document, 'click', '#btnDelete', e => {
    const filler = e.target.parentNode.parentNode
    fillModal(filler, 2)
    modalDelete.show()
})

// let fields= []

function fillModal(filler, number) {
    idForm = filler.children[0].innerHTML
    const name = filler.children[1].innerHTML
    const lastname = filler.children[2].innerHTML
    const age = filler.children[3].innerHTML
    const username = filler.children[4].innerHTML
    const password = filler.children[5].innerHTML
    ids[number - 1].value = idForm
    names[number].value = name
    lastnames[number].value = lastname
    ages[number].value = age
    usernames[number].value = username
    passwords[number].value = password
    // fields.push(idForm, username, password, name, lastname, age)
}

editSubmit.addEventListener('click', e => {

    let user = {
        "id": ids[0].value,
        "username": usernames[1].value,
        "password": passwords[1].value,
        "name": names[1].value,
        "lastname": lastnames[1].value,
        "age": ages[1].value,
    }
    const selectedRole = roles[1].value
    let role = []
    if (selectedRole === 'USER') {
        role.push({
            "id": '1',
            "name": "ROLE_USER"
        })
    } else {
        role.push({
            "id": '2',
            "name": "ROLE_ADMIN"
        })
    }

    user['roles'] = role

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then(response => {
        if (response.ok) {
            console.log('OK')
        } else {
            alert(`User with username ${usernames[1].value} already exists`);
        }
    })
})

newUserSubmit.addEventListener('click', e => {
    let user = {
        "username": usernames[0].value,
        "password": passwords[0].value,
        "name": names[0].value,
        "lastname": lastnames[0].value,
        "age": ages[0].value,
    }
    const selectedRole = roles[0].value
    let role = []
    if (selectedRole === 'USER') {
        role.push({
            "id": '1',
            "name": "ROLE_USER"
        })
    } else {
        role.push({
            "id": '2',
            "name": "ROLE_ADMIN"
        })
    }

    user['roles'] = role

    e.preventDefault()
    const newUserForm = document.querySelector('#formNewUser')
    newUserForm.checkValidity()

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then(response => {
        if (response.ok) {
            console.log('OK')
        } else {
            response.json().
            alert(`User with username ${usernames[0].value} already exists`);
        }
    })
})

deleteSubmit.addEventListener('click', e => {
    e.preventDefault()

    fetch(`${url}/${ids[1].value}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            console.log('OK')
        } else {
            console.log('Error');
        }
    })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred');
        });

    modalDelete.hide()
})

function showSpanError(field, message, num) {
    const fieldName = `.invalid${field.charAt(0).toUpperCase() + field.slice(1)}`
    const spanError = document.querySelector(field)[num]
    spanError.innerHTML = message
    spanError.hidden = false
}
