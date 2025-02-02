import React from 'react'
import Layout from '../Layout/Layout'
import Banner from '../Components/Home/Banner'
import PopularMovies from '../Components/Home/PopularMovies'
import PopularMoviesWrapper from '../Components/Home/PopularMoviesWrapper'
import MoviesPage from "./Movies";
import Test from "../Test";
// import Promos from '../Components/Home/Promos'
// import TopRated from '../Components/Home/TopRated'
const HomeScreen = () => {
  return (
    
    <Layout className='container mx-auto min-h-screen  overflow-y-auto'> 
      {/* <div className='container mx-auto min-h-screen px-2 mb-6'> */}
        <div className="px-2 mb-6">
        <Banner />
        <PopularMovies />
        <Test />
        {/* <MoviesPage/> */}
         {/* <Promos /> */}
        {/* <TopRated />  */}
        </div>
      
    </Layout>

  )
}

export default HomeScreen