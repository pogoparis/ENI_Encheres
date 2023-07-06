window.onload = init;


$(document).ready(function() {
	var message = $(".alert");

	function masquerDiv() {
		message.fadeOut(3000, function() {
			// message.remove();
		});
	}

	setTimeout(masquerDiv, 4000);
});



function init() {


	var optionVentes = document.querySelector('input[value="ventes"]');
	var optionAchats = document.querySelector('input[value="achats"]');
	var checkboxes = document.querySelectorAll('.venteCheck');
	var checkboxes2 = document.querySelectorAll('.disable2');

	if (optionVentes) {
		optionVentes.addEventListener('change', function() {
			if (optionVentes.checked) {
				checkboxes.forEach(function(checkbox) {
					checkbox.disabled = false;
				});
				checkboxes2.forEach(function(checkbox) {
					checkbox.disabled = true;
				});
			}
		});
	}

	if (optionAchats) {
		optionAchats.addEventListener('change', function() {
			if (optionAchats.checked) {
				checkboxes.forEach(function(checkbox) {
					checkbox.disabled = true;
				});
				checkboxes2.forEach(function(checkbox) {
					checkbox.disabled = false;
				});
			}
		});

	}


}