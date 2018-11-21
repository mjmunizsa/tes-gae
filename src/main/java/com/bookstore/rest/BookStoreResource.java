package com.bookstore.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bookstore.dao.BookBeanDAO;
import com.bookstore.data.BookBean;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/bookstore")
@Produces("application/json;charset=utf-8")
@Api(value = "bookstore", description = "Bookstore service")
public class BookStoreResource {

    private BookBeanDAO bookBeanDAO;
    
    private static final Logger LOGGER = Logger.getLogger(BookStoreResource.class.getName());

    public BookStoreResource() {
        this.bookBeanDAO = new BookBeanDAO();
    }

    @GET
    @ApiOperation("list books objects")
    public Response list() {
        return Response.ok(this.bookBeanDAO.list()).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation("get book object")
    public Response get(@PathParam("id") Long id) {
    	LOGGER.info("Execution get" + id);
        BookBean bean = this.bookBeanDAO.get(id);
        if (bean == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(bean).build();
    }
    
    @GET
    @Path("/search/{strsearch}")
    @ApiOperation("get book object")
    public Response get(@PathParam("strsearch") String str) {
    	LOGGER.info("Execution search:" + str);
    	List<BookBean> books= this.bookBeanDAO.listFilter(str);
        if (books== null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(books).build();
    }

    @POST
    @Consumes("application/json;charset=utf-8")
    @ApiOperation("save book object")
    public Response save(BookBean bean) {
        this.bookBeanDAO.save(bean);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation("delete book object")
    public Response delete(@PathParam("id") Long id) {
        BookBean bean = this.bookBeanDAO.get(id);
        if (bean == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.bookBeanDAO.delete(bean);
        return Response.ok().build();
    }
}
