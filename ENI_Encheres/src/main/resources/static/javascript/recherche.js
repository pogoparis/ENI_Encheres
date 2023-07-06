window.onload = init;

  function scrollWin() {
          window.scrollTo(0, 960);
        }

function init(){


//document.addEventListener('DOMContentLoaded', function() {
//		  const isFirstVisit = !document.cookie.includes('visited=true');
//		  const targetElement = document.querySelector('#page2');
//		  const currentURL = window.location.href;
//
//		  // Vérifier si l'URL contient des segments supplémentaires après l'URL de base
//		  const hasAdditionalSegments = currentURL !== window.location.origin + window.location.pathname;
//
//		  if (!isFirstVisit && targetElement && hasAdditionalSegments) {
//		    targetElement.scrollIntoView();
//		  }
//
//		  // Définir le cookie pour indiquer que l'utilisateur a visité le site
//		  if (isFirstVisit) {
//		    document.cookie = 'visited=true; max-age=3100'; 
//		  }
//		});

	
		var optionVentes = document.querySelector('input[value="ventes"]');
		var optionAchats = document.querySelector('input[value="achats"]');
		var checkboxes = document.querySelectorAll('.venteCheck');
		var checkboxes2 = document.querySelectorAll('.disable2');

		optionVentes.addEventListener('change', function () {
			if (optionVentes.checked) {	
				checkboxes.forEach(function (checkbox) {
					checkbox.disabled = false;
				});
				checkboxes2.forEach(function (checkbox) {
					checkbox.disabled = true;
				});
			}
		});

		optionAchats.addEventListener('change', function () {
			if (optionAchats.checked) {
				checkboxes.forEach(function (checkbox) {
					checkbox.disabled = true;
				});
				checkboxes2.forEach(function (checkbox) {
					checkbox.disabled = false;
				});
			}
		});
		
		
		}