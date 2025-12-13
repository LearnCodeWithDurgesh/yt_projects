import useAuth from "@/auth/store";
import { Navigate, Outlet } from "react-router";

function Userlayout() {
  const checkLogin = useAuth((state) => state.checkLogin);

  if (checkLogin())
    return (
      <div>
        <Outlet />
      </div>
    );
  else return <Navigate to={"/login"} />;
}

export default Userlayout;
