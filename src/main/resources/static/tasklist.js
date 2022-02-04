let newFormTask1 = $('#new-form-task1');
let newFormTask2 = $('#new-form-task2');
let taskList = $('#task-list');
let optionTopic = $('#option');
let optionTopic1 = $('#option1');
let buttonNewTask1 = $('#button-new-task1');
let buttonNewTask2 = $('#button-new-task2');
let buttonNewTask3 = $('#button-new-task3');
let editId = $('#edit-id');
let editTopic = $('#edit-topic');
let editNameTask = $('#edit-name-task');
let editDescription = $('#edit-description');
let editNameClass = $('#edit-name-class');
let editBytes = $('#edit-bytes');
let editIsQuestion = $('#edit-is-question');
let editListCorrectAnswers = $('#edit-list-correct-answers');
let taskEditForm = $('#task-edit-form');
let taskEditForm2 = $('#task-edit-form2');
let editCloseButton = $('#editCloseButton');

$(function () {
    getAllTasks();
});

buttonNewTask1.click(function (event) {
    event.preventDefault();
    $.ajax({
        url: '/api/topics',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            data.forEach(t => {
                let option = "";
                option += "<option name='" + t + "' className='form-control'>" + t + "</option>";
                optionTopic.append(option);
            });
        }
    });
});

buttonNewTask2.click(function (event) {
    event.preventDefault();
    $.ajax({
        url: '/api/topics',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            data.forEach(t => {
                let option = "";
                option += "<option name='" + t + "' className='form-control'>" + t + "</option>";
                optionTopic1.append(option);
            });
        }
    });
});

buttonNewTask3.click(function (event) {
    event.preventDefault();
    $.ajax({
        url: '/api/topics',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            data.forEach(t => {
                let option = "";
                option += "<option name='" + t + "' className='form-control'>" + t + "</option>";
                $('#option-filter').append(option);
            });
        }
    });
});

newFormTask1.on("submit", function (event) {
    event.preventDefault();
    $.ajax({
        url: '/api/task-is-question',
        method: 'post',
        data: newFormTask1.serialize(),
        success: function (task) {
            let newTr = $("<tr id='tr" + task.id + "'></tr>");
            let newTrTd = "";
            newTrTd += "<td>" + task.id + "</td>";
            newTrTd += "<td id='td" + task.id + "lesson_topic'>" + task.lessonTopic + "</td>";
            newTrTd += "<td id='td" + task.id + "name_task'>" + task.nameTask + "</td>";
            newTrTd += "<td id='td" + task.id + "description'>" + task.description + "</td>";
            newTrTd += "<td id='td" + task.id + "name_class'>" + task.nameClass + "</td>";
            newTrTd += "<td id='td" + task.id + "res'>" + task.res + "</td>";
            newTrTd += "<td id='td" + task.id + "is_question'>" + task.isQuestion + "</td>";
            newTrTd += "<td id='td" + task.id + "correct_answers'>" + task.correctAnswers + "</td>";
            newTrTd += "<td id='td" + task.id + "path'>" + task.path + "</td>";

            newTrTd += "<td><div class='btn-group'>" +
                "<button data-target='#editTask' data-toggle='modal' class='btn btn-primary'" +
                "onclick='openEditTaskModal(" + task.id + ")' data-userId='" + task.id + "'>Edit</button></div>" +
                "<div class='btn-group'><button class='btn btn-danger' data-userId='" + task.id + "' onclick='removeTask(" + task.id + ")'>" +
                "Delete</button></div></td>";
            newTr.html(newTrTd);
            taskList.append(newTr);
            $('#newCloseButton1').trigger("click");
            $("#id").trigger("click");
        }
    });
});

newFormTask2.on("submit", function (event) {
    event.preventDefault();
    // const $that = $(this),
    //     formData = new FormData($that.get(0));
    $.ajax({
        url: '/api/file-task',
        method: 'post',
        // processData: false,
        // contentType: false,
        data: newFormTask2.serialize(),
        success: function (task) {
            let newTr = $("<tr id='tr" + task.id + "'></tr>");
            let newTrTd = "";
            newTrTd += "<td>" + task.id + "</td>";
            newTrTd += "<td id='td" + task.id + "lesson_topic'>" + task.lessonTopic + "</td>";
            newTrTd += "<td id='td" + task.id + "name_task'>" + task.nameTask + "</td>";
            newTrTd += "<td id='td" + task.id + "description'>" + task.description + "</td>";
            newTrTd += "<td id='td" + task.id + "name_class'>" + task.nameClass + "</td>";
            newTrTd += "<td id='td" + task.id + "res'>" + task.res + "</td>";
            newTrTd += "<td id='td" + task.id + "is_question'>" + task.isQuestion + "</td>";
            newTrTd += "<td id='td" + task.id + "correct_answers'>" + task.correctAnswers + "</td>";
            newTrTd += "<td id='td" + task.id + "path'>" + task.path + "</td>";

            newTrTd += "<td><div class='btn-group'>" +
                "<button data-target='#editTask' data-toggle='modal' class='btn btn-primary'" +
                "onclick='openEditTaskModal(" + task.id + ")' data-userId='" + task.id + "'>Edit</button></div>" +
                "<div class='btn-group'><button class='btn btn-danger' data-userId='" + task.id + "' onclick='removeTask(" + task.id + ")'>" +
                "Delete</button></div></td>";
            newTr.html(newTrTd);
            taskList.append(newTr);
            $('#newCloseButton2').trigger("click");
            $("#id").trigger("click");
            window.reload(true);
        }
    });
});

function getAllTasks() {
    $.ajax({
        url: '/admin/api/tasks',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let task = data[i];
                let tr = $("<tr id='tr" + task.id + "'></tr>");
                let td = "";
                td += "<td>" + task.id + "</td>";
                td += "<td id='td" + task.id + "lesson_topic'>" + task.lessonTopic + "</td>";
                td += "<td id='td" + task.id + "name_task'>" + task.nameTask + "</td>";
                td += "<td id='td" + task.id + "description'>" + task.description + "</td>";
                td += "<td id='td" + task.id + "name_class'>" + task.nameClass + "</td>";
                td += "<td id='td" + task.id + "res'>" + task.res + "</td>";
                td += "<td id='td" + task.id + "is_question'>" + task.isQuestion + "</td>";
                td += "<td id='td" + task.id + "correct_answers'>" + task.correctAnswers + "</td>";
                td += "<td id='td" + task.id + "path'>" + task.path + "</td>";

                td += "<td>" +
                    "<button data-target='#editTask' data-toggle='modal' class='btn btn-primary'" +
                    "onclick='openEditTaskModal(" + task.id + ")' data-taskId='" + task.id + "'>Edit</button>" +
                    "<button class='btn btn-danger' data-taskId='" + task.id + "' onclick='removeTask(" + task.id + ")'>Delete</button></td>";
                tr.html(td);
                taskList.append(tr);
            }
        }
    });
}

$('#filter-form').submit(function (event) {
    event.preventDefault();
    let nameTopic = $('#filter-form').serialize().replace('lessonTopic=', '');
    getTasksByTopic(nameTopic);
});

function getTasksByTopic(topic) {
    $.ajax({
        url: '/api/tasks/filter/' + topic,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let task = data[i];
                let tr = $("<tr id='tr" + task.id + "'></tr>");
                let td = "";
                td += "<td>" + task.id + "</td>";
                td += "<td id='td" + task.id + "lesson_topic'>" + task.lessonTopic + "</td>";
                td += "<td id='td" + task.id + "name_task'>" + task.nameTask + "</td>";
                td += "<td id='td" + task.id + "description'>" + task.description + "</td>";
                td += "<td id='td" + task.id + "name_class'>" + task.nameClass + "</td>";
                td += "<td id='td" + task.id + "res'>" + task.res + "</td>";
                td += "<td id='td" + task.id + "is_question'>" + task.isQuestion + "</td>";
                td += "<td id='td" + task.id + "correct_answers'>" + task.correctAnswers + "</td>";
                td += "<td id='td" + task.id + "path'>" + task.path + "</td>";

                td += "<td><div class='btn-group'>" +
                    "<button data-target='#editTask' data-toggle='modal' class='btn btn-primary'" +
                    "onclick='openEditTaskModal(" + task.id + ")' data-taskId='" + task.id + "'>Edit</button></div>" +
                    "<div class='btn-group'><button class='btn btn-danger' data-taskId='" + task.id + "' onclick='removeTask(" + task.id + ")'>Delete</button></div></td>";
                tr.html(td);
                $('#tasksFilter').html(tr);
            }

        }
    });
}

function openEditTaskModal(id) {
    $.get('/api/task/' + id, function (task) {
        let id = task.id;
        let topic = task.lessonTopic;
        let nameTask = task.nameTask;
        let des = task.description;
        let cl = task.nameClass;
        let res = task.res;
        let isQuest = task.isQuestion;
        let correct = task.correctAnswers;

        editId.val(id);
        editTopic.val(topic);
        editNameTask.val(nameTask);
        editDescription.val(des);
        editNameClass.val(cl);
        editBytes.val(res);
        editIsQuestion.val(isQuest);
        editListCorrectAnswers.val(correct)
        $('#path-id-path').val(task.path);
    });

}

taskEditForm.submit(function (event) {
    event.preventDefault();
    $.ajax({
        url: '/api/task',
        method: "PUT",
        data: taskEditForm.serialize(),
        success:function (data) {
            let id = data.id;
            let currentTr = $("tr#tr" + id);
            currentTr.empty();
            let newTr = "";
            newTr += "<td>" + id + "</td>";
            newTr += "<td id='td" + id + "edit-topic'>" + data.lessonTopic + "</td>";
            newTr += "<td id='td" + id + "edit-name-task'>" + data.nameTask + "</td>";
            newTr += "<td id='td" + id + "edit-description'>" + data.description + "</td>";
            newTr += "<td id='td" + id + "edit-name-class'>" + data.nameClass + "</td>";
            newTr += "<td id='td" + id + "edit-res'>" + data.res + "</td>";
            newTr += "<td id='td" + id + "edit-is-question'>" + data.isQuestion + "</td>";
            newTr += "<td id='td" + id + "edit-correct-answers'>" + data.correctAnswers + "</td>";
            newTr += "<td id='td" + id + "path'>" + data.path + "</td>";

            newTr += "<td><div class='btn-group'>" +
                "<button data-target='#editTask' data-toggle='modal' class='btn btn-primary'" +
                "onclick='openEditTaskModal(" + id + ")' data-userId='" + id + "'>Edit</button></div>" +
                "<div class='btn-group'><button class='btn btn-danger' data-userId='" + id + "' onclick='removeTask(" + id + ")'>Delete</button></div></td>";
            currentTr.html(newTr);
            editCloseButton.trigger("click");
        }
    });
});


function removeTask(id) {
    $("tr#tr" + id).remove();
    $.ajax({
        url: "/api/task",
        method: "DELETE",
        data: ({
            id: id
        })
    });
}



