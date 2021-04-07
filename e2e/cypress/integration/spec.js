describe('React App Component tests', () => {
   beforeEach(() => {
      cy.visit('/')
   })

   it('Check that there are two events on main page', () => {
      cy.contains("Test event number 2")
      cy.contains("Test Marathon")
   })

   it('Go to Sign In page', () => {
      cy.get('a.nav-link')
         .contains("Sign In")
         .click()
      cy.get('input[name="email"]')
      cy.get('input[name="password"]')
      cy.get('button[type="submit"]')     
   })
})

describe('Do a complete login', () => {
   it('Insert a item in LocalStorage', () => {
      window.localStorage.setItem('name', 'Kenneth')
      expect(localStorage.getItem('name')).to.be.eq('Kenneth')
      expect(localStorage.getItem('name')).to.not.be.eq('Ken')
   })
   it('Go to Sign In page, LogIn', () => {
      cy.get('a.nav-link')
         .contains("Sign In")
         .click()
      cy.get('input[name="email"]')
         .type("admin@lop.no")
      cy.get('input[name="password"]')
         .type("admin")
      cy.get('button[type="submit"]')
         .click()
         .should(() => {
            expect(localStorage.getItem('token')).to.contains('eyJhbGciOiJIUzUxMiJ9.')
         })
   })

})