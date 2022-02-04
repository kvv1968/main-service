
let userList = $("#user-list");

let deleteButton = $("#deleteButton");


let userEditForm = $("#userEditForm");
let editId = $("#editId");
let editUserUsername  = $('#editUserUsername');
let editUserPassword = $("#editUserPassword");
let editUserFirstName = $("#editUserFirstName");
let editUserLasName = $("#editUserLasName");
let editEmail = $("#editEmail");
let editPhone = $("#editPhone");
let editRole = $("#edit-roles");
let editNewEmail = $("#newUserEmail");

let editCloseButton = $("#editCloseButton");

let newCloseButton = $("#newCloseButton");
let newUser ;
let newFUser ;
let newFormUser = $("#new-form-user");
let newUserUsername = $("#newUserUsername");
let newUserPassword = $("#newUserPassword");
let newUserFirstName ;
let newUserLasName ;
let newUserEmail ;
let newUserRole = $("#new-user-role");

let newRoleView ;
let AddNewUserInput ;
let allRoles = [];
let newUserFormResetButton = $("#newUserFormResetButton");

let editDate = $("#edit-date");
let editEnabled = $("#edit-enable");


$(function () {
    getAllUsers();
    loadAllRoles();
});


function getAllUsers() {
    $.get("/admin/users", function (data) {
        for (let i = 0; i < data.length; i++) {
            let user = data[i];

            let tr = $("<tr id='tr" + user.id + "'></tr>");
            let td = "";
            td += "<td>" + user.id + "</td>";
            td += "<td id='td" + user.id + "username'>" + user.username + "</td>";
            td += "<td id='td" + user.id + "password'>" + user.password + "</td>";
            td += "<td id='td" + user.id + "first_name'>" + user.firstName + "</td>";
            td += "<td id='td" + user.id + "last_name'>" + user.lastName + "</td>";
            td += "<td id='td" + user.id + "email'>" + user.email + "</td>";
            td += "<td id='td" + user.id + "phone'>" + user.phone + "</td>";
            td += "<td id='td" + user.id + "date'>" + user.date + "</td>";
            td += "<td id='td" + user.id + "enabled'>" + user.enabled + "</td>";
            td += "<td id='td" + user.id + "role' class='text-uppercase'>" + user.role.role + "</td>";

            td += "<td><div class='btn-group'>" +
                "<button data-target='#editUser' data-toggle='modal' class='btn btn-primary'" +
                "onclick='openEditUserModal(" + user.id + ")' data-userId='" + user.id + "'>Edit</button></div>" +
                "<div class='btn-group'><button class='btn btn-danger' data-userId='" + user.id + "' onclick='removeUser(" + user.id + ")'>Delete</button></div></td>";
            tr.html(td);
            userList.append(tr);


        }
    });
}

function openEditUserModal(id) {

    $.get("/admin/users/"+id, function (user) {
        $('input[name=id]').val(user.id);
        $('input[name=username]').val(user.username);
        $('input[name=password]').val(user.password);
        $('input[name=firstName]').val(user.firstName);
        $('input[name=lastName]').val(user.lastName);
        $('input[name=email]').val(user.email);
        $('input[name=phone]').val(user.phone);
        $('input[name=date]').val(user.date);

        let enable = user.enabled;
        let updateEnable = "<strong>Включен - " + enable + "</strong><br>" +
            "<label class='font-weight-bold'>да\n" +
            "<input type='radio' name='enabled' value='true' " + (enable ? 'checked' : '') + "></label>\n" +
            "<label class='font-weight-bold'>нет\n" +
            "<input type='radio' name='enabled' value='false' " + (enable ? '' : 'checked') + "></label>";
        editEnabled.text(enable);
        $('#div-enable').html(updateEnable);

        let id = user.role.id;
        let elem = document.getElementById("editUserRole-"+id);
        elem.checked = true;

        // let updateRole = "<br><strong style='color: '>Включена роль - " + role + "</strong>"
        // $('#edit-roles').append(updateRole);

    });

}



userEditForm.submit(function (event) {
    event.preventDefault();
    let updateUser = userEditForm.serialize();
    $.ajax({
        url: '/user/users',
        method: "PUT",
        data: updateUser,
        success: function (data) {
            let id = data.id;
            let currentTr = $("tr#tr" + id);
            currentTr.empty();
            let newTr = "";
            newTr += "<td>" + id + "</td>";
            newTr += "<td id='td" + id + "username'>" + data.username + "</td>";
            newTr += "<td id='td" + id + "password'>" + data.password + "</td>";
            newTr += "<td id='td" + id + "firstName'>" + data.firstName + "</td>";
            newTr += "<td id='td" + id + "lastName'>" + data.lastName + "</td>";
            newTr += "<td id='td" + id + "email'>" + data.email + "</td>";
            newTr += "<td id='td" + id + "phone'>" + data.phone + "</td>";
            newTr += "<td id='td" + id + "date'>" + data.date + "</td>";
            newTr += "<td id='td" + id + "enabled'>" + data.enabled + "</td>";

            newTr += "<td id='td" + id + "roles' class='text-uppercase'>" + data.role.role + "</td>";

            newTr += "<td><div class='btn-group'>" +
                "<button data-target='#editUser' data-toggle='modal' class='btn btn-primary'" +
                "onclick='openEditUserModal(" + id + ")' data-userId='" + id + "'>Edit</button></div>" +
                "<div class='btn-group'><button class='btn btn-danger' data-userId='" + id + "' onclick='removeUser("+id+")'>Delete</button></div></td>";
            currentTr.html(newTr);
            $("#editCloseButton").click();
        }
    });



});

function removeUser(id) {
    $("tr#tr" + id).remove();
    $.ajax({
        url: "/admin/users/delete",
        method: "POST",
        data: ({
            id : id
        })
    });
    /* $.get("/admin/users/delete/"+id);
     $.post("/admin/users/delete", id_, function (g) {
         alert(g);
     });*/
}

function loadAllRoles(){
    $.get("/admin/users/roles", function (data) {

        for (let i = 0; i < data.length; i++) {
            // allRoles[i] = data[i];
            let role = data[i].role;
            let id = data[i].id;
            newUserRole.append("<label class='font-weight-bold custom-control-label text-uppercase'>" +
                "<input type='radio' class='custom-control-input' name='role' id='newUserRole" + role + "' value='" + role + "' " + (role === 'user' ? 'checked':'') + ">" + role + "</label>");

            editRole.append("<label class='font-weight-bold custom-control-label text-uppercase'>" +
                "<input type='radio' class='custom-control-input' name='role' id='editUserRole-" + id + "' value='" + role + "'>" + role + "</label>");

        }

    });
}


newFormUser.on("submit", function (event) {
    event.preventDefault();
    $.post('/admin/users', newFormUser.serialize(), function(user){

        let newTr = $("<tr id='tr" + user.id + "'></tr>");
        let newTrTd = "";
        newTrTd += "<td>" + user.id + "</td>";
        newTrTd += "<td id='td" + user.id + "username'>" + user.username + "</td>";
        newTrTd += "<td id='td" + user.id + "password'>" + user.password + "</td>";
        newTrTd += "<td id='td" + user.id + "first_name'>" + user.firstName + "</td>";
        newTrTd += "<td id='td" + user.id + "last_name'>" + user.lastName + "</td>";
        newTrTd += "<td id='td" + user.id + "email'>" + user.email + "</td>";
        newTrTd += "<td id='td" + user.id + "phone'>" + user.phone + "</td>";
        newTrTd += "<td id='td" + user.id + "date'>" + user.date + "</td>";
        newTrTd += "<td id='td" + user.id + "enabled'>" + user.enabled + "</td>";
        newTrTd += "<td id='td" + user.id + "roles' class='text-uppercase'>" + user.role.role + "</td>";

        newTrTd += "<td><div class='btn-group'>" +
            "<button data-target='#editUser' data-toggle='modal' class='btn btn-primary'" +
            "onclick='openEditUserModal(" + user.id + ")' data-userId='" + user.id + "'>Edit</button></div>" +
            "<div class='btn-group'><button class='btn btn-danger' data-userId='" + user.id + "' onclick='removeUser(" + user.id + ")'>" +
            "Delete</button></div></td>";
        newTr.html(newTrTd);
        userList.append(newTr);
        newCloseButton.click();
        $("#id").click();
    });
});


/*for(let i =0; i<data.length; i++){
let user = data[i];




}*/


/*function getUserTable(username, password) {
    $.get("/admin/users/new/" + username +"/" +password, function (user) {

        let role = "";
        let  userRoles = user.roles;
        for (let j = 0; j< userRoles.length; j++) {
            role += userRoles[j].role + " ";
        }
        let newTr = $("<tr id='tr" + user.id + "'></tr>");
        let newTrTd = "";
        newTrTd += "<td>" + user.id + "</td>";
        newTrTd += "<td id='td" + user.id + "username'>" + user.username + "</td>";
        newTrTd += "<td id='td" + user.id + "password'>" + user.password + "</td>";
        newTrTd += "<td id='td" + user.id + "first_name'>" + user.first-name + "</td>";
        newTrTd += "<td id='td" + user.id + "last_name'>" + user.last-name + "</td>";
        newTrTd += "<td id='td" + user.id + "email'>" + user.email + "</td>";
        newTrTd += "<td id='td" + user.id + "roles' class='text-uppercase'>" + role + "</td>";

        newTrTd += "<td><div class='btn-group mr-sm-1'><button class='btn btn-primary' " +
            "data-toggle='modal' data-target='#editUserModal' onclick='openEditUserModal(" + user.id + ")' " +
            "data-userId='" + user.id + "'>Edit</button></div>" +
            "<div class='btn-group'><button class='btn btn-danger' data-userId='" + user.id + "' onclick='removeUser(" + user.id + ")'>" +
            "Delete</button></div></td>";
        newTr.html(newTrTd);
        userList.append(newTr);
        newCloseButton.trigger("click");
    });
}*/


