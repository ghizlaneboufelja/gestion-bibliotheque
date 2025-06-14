$(document).ready(function() {
    // Gestion du bouton Modifier
    $('table #editButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(livre, status) {

            console.log("Livre reçu (edit) :", livre);

            // Remplissage du formulaire de modification
            $('#editId').val(livre.idLivre);
            $('#editTitre').val(livre.titre);
            $('#editauteur').val(livre.auteur);
            $('#editAnnee_edition').val(livre.annee_edition);

            // Sélection de la catégorie - version corrigée
            if (livre.categorie) {
                $('#editcategorie').val(livre.categorie.idCategorie);
            } else {
                $('#editcategorie').val('');
            }

            // Correction du nom du champ pour les exemplaires disponibles
            $('#editNbrExempDisponibles').val(livre.nbrExempDisponibles);
        });

        $('#EditModal').modal('show');
    });

    // Gestion du bouton Détails
    $('table #detailsButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(livre, status) {

            console.log("Livre reçu (edit) :", livre);
            // Remplissage du modal de détails
            $('#detailsId').val(livre.idLivre);
            $('#detailstitre').val(livre.titre);
            $('#detailsauteur').val(livre.auteur);
            $('#detailsAnnee_edition').val(livre.annee_edition);

            if (livre.categorie && livre.categorie.nom) {
                $('#detailscategorie').val(livre.categorie.nom);
            } else {
                $('#detailscategorie').val('Non définie');
            }

            // Affichage du nombre d'exemplaires (dans un span, pas un input)
            $('#detailsNbrExempDisponibles').val(livre.nbrExempDisponibles ?? 'N/A');
        });

        $('#detailsModal').modal('show');
    });

    // Gestion du bouton Supprimer
    $('table #deleteButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmDeleteButton').attr('href', href);
        $('#deleteModal').modal('show');
    });


    $('.table #photoButton').on('click',function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#photoModal #employeePhoto').attr('src', href);
        $('#photoModal').modal();
    });

});