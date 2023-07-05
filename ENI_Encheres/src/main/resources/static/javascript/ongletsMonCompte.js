window.onload = init;

function init(){

document.getElementById("ongletArticlesAchetes").addEventListener('click', onglet1);
document.getElementById("ongletEncheres").addEventListener('click', onglet2);
document.getElementById("ongletArticlesVendus").addEventListener('click', onglet3);
}

function onglet1(event){
	document.getElementById("ongletArticlesAchetes").style.backgroundColor = "#f8c0c2";
	document.getElementById("ongletEncheres").style.backgroundColor = "white";
	document.getElementById("ongletArticlesVendus").style.backgroundColor = "white";
	
	document.getElementById("titre1").style.color = "white";
	document.getElementById("titre2").style.color = "black";
	document.getElementById("titre3").style.color = "black";
	
	document.getElementById("listeArticlesAchetes").style.display = "block";
	document.getElementById("listeEncheres").style.display = "none";
	document.getElementById("listeArticlesVendus").style.display = "none";
}

function onglet2(event){
	document.getElementById("ongletArticlesAchetes").style.backgroundColor = "white";
	document.getElementById("ongletEncheres").style.backgroundColor = "#f8c0c2";
	document.getElementById("ongletArticlesVendus").style.backgroundColor = "white";
	
	document.getElementById("titre1").style.color = "black";
	document.getElementById("titre2").style.color = "white";
	document.getElementById("titre3").style.color = "black";
	
	document.getElementById("listeArticlesAchetes").style.display = "none";
	document.getElementById("listeEncheres").style.display = "block";
	document.getElementById("listeArticlesVendus").style.display = "none";
}

function onglet3(event){
	document.getElementById("ongletArticlesAchetes").style.backgroundColor = "white";
	document.getElementById("ongletEncheres").style.backgroundColor = "white";
	document.getElementById("ongletArticlesVendus").style.backgroundColor = "#f8c0c2";
	
	document.getElementById("titre1").style.color = "black";
	document.getElementById("titre2").style.color = "black";
	document.getElementById("titre3").style.color = "white";
	
	document.getElementById("listeArticlesAchetes").style.display = "none";
	document.getElementById("listeEncheres").style.display = "none";
	document.getElementById("listeArticlesVendus").style.display = "block";
}


