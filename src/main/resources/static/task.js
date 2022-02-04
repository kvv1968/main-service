let newFormTask = $('#new-form-task');
let taskList = $('#task-list');


newFormTask.on("submit", function (event) {
    event.preventDefault();
    let task_ = newFormTask.serialize();
    $.post('/api/task', task_, function(task){

        let newTr = $("<tr id='tr" + task.id + "'></tr>");
        let newTrTd = "";
        newTrTd += "<td>" + task.id + "</td>";
        newTrTd += "<td id='td" + task.id + "lesson_topic'>" + task.lessonTopic.topic + "</td>";
        newTrTd += "<td id='td" + task.id + "name_task'>" + task.nameTask + "</td>";
        newTrTd += "<td id='td" + task.id + "description'>" + task.description + "</td>";
        newTrTd += "<td id='td" + task.id + "name_class'>" + task.nameClass + "</td>";
        newTrTd += "<td id='td" + task.id + "bytes'>" + task.res + "</td>";
        newTrTd += "<td id='td" + task.id + "is_question'>" + task.isQuestion + "</td>";
        newTrTd += "<td id='td" + task.id + "list_correct_answers'>" + task.correctAnswers + "</td>";

        // newTrTd += "<td><div class='btn-group'>" +
        //     "<button data-target='#editUser' data-toggle='modal' class='btn btn-primary'" +
        //     "onclick='openEditUserModal(" + user.id + ")' data-userId='" + user.id + "'>Edit</button></div>" +
        //     "<div class='btn-group'><button class='btn btn-danger' data-userId='" + user.id + "' onclick='removeUser(" + user.id + ")'>" +
        //     "Delete</button></div></td>";
        newTr.html(newTrTd);
        taskList.append(newTr);
        // newCloseButton.trigger("click");
        // $("#id").trigger("click");
    });
});