<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/searchbar.css">
<form action="ProductManagement" method="post" id="searchForm">
    <div class="search-and-sort">
        <div style="display:flex;justify-content: space-between">
            <div class="search-container">
                <input type="text" name="searchProduct" id="search" placeholder="Search...">
                <button type="submit" id="submit"><i class="fas fa-search"></i> Search</button>
                <input type="hidden" name="action" value="search">
            </div>
            <div class="sort-container">
                <label for="sort">Sort by:</label>
                <select id="sort" name="sort" ">
                    <option value="title" ${param.sort == 'title' ? 'selected' : '' } default>Name</option>
                    <option value="quantityInStock" ${param.sort == 'quantityInStock' ? 'selected' : ''}>Quantity</option>
                    <option value="price" ${param.sort == 'price' ? 'selected' : ''}>Price</option>
                </select>

                <select id="order" name="order" ">
                    <option value="ASC" ${param.order == 'ASC' ? 'selected' : ''} default>Ascending</option>
                    <option value="DESC" ${param.order == 'DESC' ? 'selected' : ''}>Descending</option>
                </select>
            </div>
        </div>
    </div>
</form>

