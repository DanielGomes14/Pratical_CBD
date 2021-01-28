from neo4j import GraphDatabase,time
from datetime import date

class Service:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()

    def print_greeting(self, message):
        with self.driver.session() as session:
            greeting = session.write_transaction(self._create_and_return_greeting, message)
            print(greeting)

    
    def create_nodes(self):
        with self.driver.session() as session:
            print("Creating Nodes...")
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///yelp-1.csv' AS ROW MERGE (u:User {id:ROW.user_id})\
                MERGE (c:Company{id:ROW.business_id})\
                MERGE (r:Review{id:ROW.review_id})\
                SET r.text = ROW.text, r.date=date(ROW.date),r.stars=toInteger(ROW.stars),\
                r.funny=toInteger(ROW.funny), r.useful = toInteger(ROW.useful), r.cool = toInteger(ROW.cool)"
            )
            print("Success Creating Nodes!")
    def create_relations(self):
        with self.driver.session() as session:
            print("Creating Relations..")
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///yelp-1.csv' AS Row\
                MATCH (u:User {id:Row.user_id}),(c:Company {id:Row.business_id}), (r:Review{id:Row.review_id})\
                create (u)-[:WRITES]->(r)\
                create (r)-[:EVALUATES]->(c)"
            )
            print("Success creating Relations!")

    def get_all_users(self):        
        querie = "MATCH (u:User) return u"
        results=self.run_querie(querie)
        for r in results:
            print(f"User: {r['u']['id']}")
    
    def get_num_revphrases(self):
        query = "MATCH (r:Review)\
                return r.id,size(split(r.text,'.')) as num_phrases\
                ORDER BY num_phrases DESC LIMIT 10"
        results=self.run_querie(query)
        for r in results:
            print(f"Review ID: {r['r.id']}, Number of Phrases: {r['num_phrases']}")

    def get_top_num_reviews(self):
        query =  "MATCH (u:User)-[w:WRITES]->(r:Review)\
        return u as user, count(w) as total_reviews\
        ORDER BY total_reviews DESC LIMIT 10"
        results=self.run_querie(query)
        for r in results:
            print(f"User: {r['user']['id']}, Total Number of Reviews Written: {r['total_reviews']}" )

    def get_oldest_review(self):
        query = "MATCH (u:User)-[:WRITES]->(r:Review)\
                return u,r.date\
                ORDER BY r.date\
                LIMIT 1"
        results=self.run_querie(query)
        for r in results:
            print(f"User: {r['u']['id']}, Date of Review: {(str(r['r.date']))}")

    def get_top_evaluations(self):
        query = "MATCH (r:Review)-[e:EVALUATES]->(c:Company)\
                return count(r) as num_reviews, c as company \
                ORDER BY  num_reviews DESC \
                LIMIT 1"
        results=self.run_querie(query)
        for r in results:
            print(f"Company : {r['company']['id']}, number of Reviews: {r['num_reviews']}" )

    def get_reviews(self,rating,date):
        query = "match (r:Review{stars:" + rating + "})\
                WHERE r.date > date(" + date + ")\
                return  r.date as date ,r.id as id,r.stars as stars "
        results=self.run_querie(query)
        for r in results:
            print(f"Date Review: {str(r['date'])}, ID: {r['id']}, stars: {r['stars']}" )

    def get_avg_evaluation(self):
        query = "match(r:Review)-[:EVALUATES]->(c:Company)\
                with c as company, avg(r.stars) as avg_rating\
                return company, round(avg_rating*100)/100 as avg_rating"
        results=self.run_querie(query)
        for r in results:
            print(f"Company : {r['company']['id']}, Average Rating: {r['avg_rating']}" )
    

    def get_avg_user_givenstars(self):
        query = "Match(u:User)-[:WRITES]->(r:Review)\
                with u, avg(r.stars) as avg_rat\
                return u, round(avg_rat*100)/100 as avg_rat "
        results=self.run_querie(query)
        for r in results:
            print(f"User : {r['u']['id']}, Average Rating Given : {r['avg_rat']}" )
    
    def get_user_to_company_path(self,id):
        query = " MATCH (u:User{id:" + id  + "}), (c:Company)\
                MATCH p=shortestPath((u)-[*]->(c))\
                where u <> c\
                RETURN p"
        results = self.run_querie(query)
        for r in results:
            for i in r['p']:
                print(i)
            print("\n ")

    def most_reacted_company_review(self):
        query = "MATCH (r:Review)-[:EVALUATES]->(c:Company)\
                with r,c,[r.funny,r.useful,r.cool] as reactions\
                return r.id,c,reduce(total=0, number in reactions | total + number) as total\
                ORDER BY total DESC\
                LIMIT 1;"
        results=self.run_querie(query)
        for r in results:
            print(f"Review: {r['r.id']},  Company: {r['c']['id']}, Total Number of Reactions : {r['total']}" )

    def run_querie(self,querie):
        return self.driver.session().run(querie).data()
if __name__ == "__main__":
    neo4jservice = Service("bolt://localhost:7687", "neo4j", "yelpdb")
    neo4jservice.create_nodes()
    neo4jservice.create_relations()
    neo4jservice.get_all_users()
    neo4jservice.get_num_revphrases()
    neo4jservice.get_top_num_reviews()
    neo4jservice.get_oldest_review()
    neo4jservice.get_top_evaluations()
    neo4jservice.get_reviews("4",'\'2011-01-01\'')
    neo4jservice.get_avg_evaluation()
    neo4jservice.get_avg_user_givenstars()
    neo4jservice.get_user_to_company_path('\'fczQCSmaWF78toLEmb0Zsw\'')
    neo4jservice.most_reacted_company_review()
    neo4jservice.close()