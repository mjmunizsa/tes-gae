package com.bookstore.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.bookstore.data.BookBean;
import com.bookstore.search.SearchIndexManager;
import com.bookstore.utils.Tokenizer;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.googlecode.objectify.ObjectifyService;


/**
 * @author mjmuniz
 *
 */
public class BookBeanDAO {

    private static final Logger LOGGER = Logger.getLogger(BookBeanDAO.class.getName());

    /**
     * @return list of books order by author
     */
    public List<BookBean> list() {
        LOGGER.info("Retrieving list of books");
        return ObjectifyService.ofy().load().type(BookBean.class).list();
    }
    
    public List <BookBean> findByIdList(List<Long> idList) {
    	LOGGER.info("Retrieving list of books for idList" + idList.toString());
    	Long [] ids =(Long[]) idList.toArray(new Long[idList.size()]);
    	Map <Long,BookBean> result = ObjectifyService.ofy().load().type(BookBean.class).ids(ids);
    	return new ArrayList<BookBean>(result.values());
    }
    
    /**
     * @return list of books order by author
     */
    public List<BookBean> listFilter(String searchText) {
        LOGGER.info("Retrieving list of books filtered " + searchText);
        //Check if the search Text is not empty
        List<BookBean> booksList = new ArrayList<BookBean>();
		if (searchText != null && !searchText.isEmpty()) {
			//Retrieve the list of Documents that match the SearchText entered.
			Results<ScoredDocument> results = SearchIndexManager.INSTANCE.retrieveDocuments(searchText);
			List<Long> idList = new ArrayList<Long>();
			for (ScoredDocument scoredDocument : results) {
				String id = scoredDocument.getId();
				idList.add(Long.parseLong(id));
			}
			booksList = findByIdList(idList);
			
		} 
		return booksList;
			
			
        //return ObjectifyService.ofy().load().type(BookBean.class).filter("author", filter).list();
    }

    /**
     * @param id
     * @return test bean with given id
     */
    public BookBean get(Long id) {
        LOGGER.info("Retrieving bean " + id);
        return ObjectifyService.ofy().load().type(BookBean.class).id(id).now();
    }

    /**
     * Saves given bean
     * @param bean
     */
    public void save(BookBean bean) {
        if (bean == null) {
            throw new IllegalArgumentException("null book object");
        }
        
        ObjectifyService.ofy().save().entity(bean).now();
        
       //Build a Document Object
  		//Add all the attributes on which search can be done
        String indexToken = Tokenizer.tokenize(bean.getAuthor() + " " + bean.getTitle());
  		Document newDoc = Document.newBuilder().setId(bean.getId().toString())
  				.addField(Field.newBuilder().setName("indexToken").setText(indexToken)).build();
  		//Add the Document instance to the Search Index
  		SearchIndexManager.INSTANCE.indexDocument("BooksDirectoryIndex", newDoc);
  		LOGGER.info("Saving book " + bean.getId());
    }

    /**
     * Deletes given bean
     * @param bean
     */
    public void delete(BookBean bean) {
        if (bean == null) {
            throw new IllegalArgumentException("null book object");
        }
        LOGGER.info("Deleting bean " + bean.getId());
        SearchIndexManager.INSTANCE.deleteDocumentFromIndex(bean.getId().toString());
        ObjectifyService.ofy().delete().entity(bean);
        
    }
}
