const editBtn = document.getElementById('editButton')
editBtn.addEventListener('click', function (event)  {
    event.stopPropagation()
    console.log('********************* Hello from JS! *********************', event.type)
})
