<!DOCTYPE html>
<html lang="pl">

    <head>
    <meta charset="UTF-8">
    <title>DODAJ KSIĄŻKĘ</title>

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="myCss.css">

    <script src="js/additional-methods.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/jquery.validate.min.js"></script>

    </head>

    <body>

    <div id="divFormAddBook" class="container">
    <form action="/start" method="post" id="formAddBook" class="form-signin">
    <h2 class="form-signin-heading">Dodawanie książki</h2>


<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputTitle" name="inputTitle" placeholder="Tytuł" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputAuthor" name="inputAuthor" placeholder="Autor" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputKategory" name="inputKategory" placeholder="Kategoria" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputPublishingHouse" name="inputPublishingHouse" placeholder="Wydawnictwo" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="year" class="form-control" id="inputDate" name="inputDate" placeholder="Rok wydania" required autofocus>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputPlace" name="inputPlace" placeholder="Miejsce wydania" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="number" class="form-control" id="inputPages" name="inputPages" placeholder="Liczba stron" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputDescription" name="inputDescription" placeholder="Opis" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputLanguage" name="inputLanguage" placeholder="Język" required>
</div>
</div>


<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text" class="form-control" id="inputIsbn" name="inputIsbn" placeholder="Isbn" required>
</div>
</div>

<div class="form-group">
    <label></label>
    <div id="wiersze">
    <input type="text">
    </div>
    </div>




    <button class="btn btn-lg btn-primary btn-block" class="btn btn-primary" type="submit">Dodaj</button>

    </form>
    </div>




    <script type="text/javascript">
    $.validator.addMethod("postalcode", function(value, element) {
        return this.optional(element) || /[0-9][0-9]-[0-9][0-9][0-9]/.test(value);
    }, "Wprowadz kod pocztowy poprawny.");


$.validator.setDefaults({
    submitHandler: function() {
        alert("Dodano użytkownika!")
    }
});

$(document).ready(function() {
    $("#formSignUp").validate({
        rules: {
            inputEmail: {
                required: true,
                email: true
            },
            inputLogin: "required",
            inputPassword: {
                required: true,
                minlength: 5
            },
            inputName: {
                required: true,
                //lettersonly: true
            },
            inputSurname: {
                required: true
            },
            inputDateBirth: {
                date: true,
                required: true
            },
            inputAddress: {
                required: true
            },
            inputPostalCode: {
                required: true,
                postalcode: true,
                minlength: 6,
                maxlength: 6
            },
            inputCity: {
                required: true,
                //lettersonly: true
            },
            inputPhone: {
                required: true,
                digits: true,
                minlength: 7
            },
            inputPermission: "required",
            inputRegulations: "required"
        },
        messages: {
            inputEmail: {
                email: "Proszę wpisać prawidłowy adres email",
                required: "Proszę wpisać adres email"
            },
            inputLogin: "Proszę wpisać login",
            inputPassword: {
                required: "Proszę wpisać hasło",
                minlength: "Twoje hasło powinno zawierać co najmniej 5 znaków"
            },
            inputName: {
                required: "Proszę wpisać imię",
                // lettersonly: "Twoję imię powinno składać się z samych liter"
            },
            inputSurname: {
                required: "Proszę wpisać nazwisko"
            },
            inputDateBirth: {
                required: "Proszę wpisać datę urodzenia",
                date: "Proszę wpisać datę w poprawnym formacie np. 2013-12-30"
            },
            inputAddress: "Proszę wpisać swój adres",
            inputPostalCode: {
                required: "Proszę wpisać swój kod pocztowy",
                postalcode: "Proszę wpisać swój kod pocztowy w prawidłowym formacie np 38-207"
            },
            inputCity: "Proszę wpisać swoje miasto",
            inputPhone: {
                required: "Proszę wpisać swój numer telefonu",
                digits: "Proszę wpisać tylko cyfry"
            },
            inputPermission: "Proszę zaakceptować",
            inputRegulations: "Proszę zaakceptować",
        },
        errorElement: "em",
        errorPlacement: function(error, element) {
            // Add the `help-block` class to the error element
            error.addClass("help-block");

            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents("#wiersze").addClass("has-feedback");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else {
                error.insertAfter(element);
            }

            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!element.next("span")[0]) {
                $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
            }
        },
        success: function(label, element) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!$(element).next("span")[0]) {
                $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
            }
        },
        highlight: function(element, errorClass, validClass) {
            $(element).parents("#wiersze").addClass("has-error").removeClass("has-success");
            $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parents("#wiersze").addClass("has-success").removeClass("has-error");
            $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
        }

    });
});

</script>



<script src="js/bootstrap.min.js"></script>
    </body>

    </html>
