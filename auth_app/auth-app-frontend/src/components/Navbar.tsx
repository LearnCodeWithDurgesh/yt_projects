import React from "react";
import { Button } from "./ui/button";
import { NavLink, useNavigate } from "react-router";
import useAuth from "@/auth/store";

function Navbar() {
  const checkLogin = useAuth((state) => state.checkLogin);
  const user = useAuth((state) => state.user);
  const logout = useAuth((state) => state.logout);
  const navigate = useNavigate();

  return (
    <nav className="py-5  dark:border-b border-gray-700 md:py-0 flex md:flex-row gap-4 md:gap-0 flex-col md:h-14 justify-around items-center    ">
      {/* brand */}
      <div className="font-semibold items-center flex gap-2">
        <span className="inline-block text-center h-6 w-6 rounded-md bg-gradient-to-r from-primary to-primary/40">
          {"A"}
        </span>
        <span className="text-base tracking-tight">Auth App</span>
      </div>

      <div className="flex gap-4 items-center">
        {checkLogin() ? (
          <>
            <NavLink to={"/dashboard/profile"}>{user?.name}</NavLink>

            <Button
              onClick={() => {
                logout();
                navigate("/");
              }}
              size={"sm"}
              className="cursor-pointer"
              variant={"outline"}
            >
              Logout
            </Button>
          </>
        ) : (
          <>
            <NavLink to={"/"}>Home</NavLink>
            <NavLink to={"/login"}>
              <Button
                size={"sm"}
                className="cursor-pointer"
                variant={"outline"}
              >
                Login
              </Button>
            </NavLink>
            <NavLink to={"/signup"}>
              <Button
                size={"sm"}
                className="cursor-pointer"
                variant={"outline"}
              >
                Signup
              </Button>
            </NavLink>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
