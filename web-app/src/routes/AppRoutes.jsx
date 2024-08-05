import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Login from "../components/Login";
import Home from "../components/Home";
import Authenticate from "../components/Authenticate";
import Registration from "../pages/Registration";

const AppRoutes = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/authenticate" element={<Authenticate/>}/>
                <Route path="/" element={<Home/>}/>
                <Route path="/registration" element={<Registration/>}/>
            </Routes>
        </Router>
    );
};

export default AppRoutes;
