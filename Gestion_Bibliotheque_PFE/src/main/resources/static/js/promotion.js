$(document).ready(function() {
    // Gestion du bouton Modifier
    $('table #editButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(promotion, status) {
            // Remplissage du formulaire de modification
            $('#editId').val(promotion.id);
            $('#edittitre').val(promotion.titre);
            $('#editdescription').val(promotion.description);
            $('#editduree').val(promotion.duree);

            // Remplir les livres sélectionnés (liste d'IDs)
            const livreIds = promotion.livres.map(livre => livre.id_livre);
            $('#editlivres').val(livreIds);

            $('#editnbrPromLimite').val(promotion.nbrPromLimite);
        });

        $('#EditModal').modal('show');
    });

    // Gestion du bouton Détails
    $('table #detailsButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function(promotion, status) {
            $('#detailsId').val(promotion.id);
            $('#detailstitre').val(promotion.titre);
            $('#detailsdescription').val(promotion.description);
            $('#detailsduree').val(promotion.duree);

            // Remplir les livres sélectionnés
            const livreIds = promotion.livres.map(livre => livre.id_livre);
            $('#detailslivres').val(livreIds);

            $('#detailsnbrPromLimite').val(promotion.nbrPromLimite);
        });

        $('#detailsModal').modal('show');
    });

    $('table #deleteButton').on('click' ,function(event){
        event.preventDefault();


        var href = $(this).attr('href');

        $('#confirmDeleteButton').attr('href', href);

        $('#deleteModal').modal();


    });
});
