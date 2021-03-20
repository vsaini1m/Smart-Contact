function seacrContact(){

	let query=$("#searchByName").val();
	
	if(query==""){
		 $(".search-result").hide();
	}else{
		 let url="/search/"+query;
		 let t;
	
	console.log('working....');
		 fetch(url)
		 	.then((response) => {
		 		return response.json();
		 	})
		 		.then((data)=>{
		 			console.log(data);

		 				let text=`<div class='list-group'>`;

		 					data.forEach((contact) =>{
		 						
		 						
		 						text+=`<a  href='/user/contact/${contact.id}' class='list-group-item list-group-action'><img src='/img/contact/${contact.image}' style='width:50px;height:50px;border-radius:50px;'> ${contact.name} (${contact.email}) </a>`;
		 					});

		 			text+=`</div>`;

		 			 $(".search-result").html(text);
	
		 		 $(".search-result").show();
		 		});
			
		
		 
		 
			
	}



}