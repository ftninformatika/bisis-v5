<#ftl encoding="utf-8">
<html>
<head>
<title>Инфо</title>
<style>
body { font-family:"Arial, Helvetica"; font-size: 11pt}
</style>
</head>
<body>
<table>
    <tr>
        <td><b>Број корисника:</b></td>
        <td>${userId!""}</td>
    </tr>
	<tr>
		<td><b>Име:</b></td>
		<td>${firstName!""}</td>
	</tr>
	<tr>
		<td><b>Презиме:</b></td>
		<td>${lastName!""}</td>
	</tr>
	<tr>
		<td><b>Име оца:</b></td>
		<td>${parentName!""}</td>
	</tr>
	<tr>
		<td><b>Адреса:</b></td>
		<td>${address!""}</td>
	</tr>
	<tr>
		<td><b>Место:</b></td>
		<td>${city!""}</td>
	</tr>
    <tr>
        <td><b>Телефон:</b></td>
        <td>${phone!""}</td>
    </tr>
	<tr>
		<td><b>ЈМБГ:</b></td>
		<td>${jmbg!""}</td>
	</tr>
	<tr>
		<td><b>Документ:</b></td>
		<td>${docNo!""}</td>
	</tr>
	<tr>
		<td><b>Врста чланства:</b></td>
		<td>${(membershipType.description)!""}</td>
	</tr>
	<tr>
		<td><b>Категорија корисника:</b></td>
		<td>${(userCategory.description!"")}</td>
	</tr>
	<tr>
		<td><b>Напомена:</b></td>
		<td>${note!""}</td>
	</tr>
	<tr>
		<td><b>Стари бројеви:</b></td>
		<td>${oldNumbers!""}</td>
	</tr>
	<tr>
		<td><b>Датум последњег учлањења:</b></td>
		<td>${signings[0].signDate?string('dd.MM.yyyy')}</td>
	</tr>
</table>

</body>
</html>